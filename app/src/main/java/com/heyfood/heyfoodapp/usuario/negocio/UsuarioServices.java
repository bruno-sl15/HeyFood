package com.heyfood.heyfoodapp.usuario.negocio;

import android.content.Context;

import com.heyfood.heyfoodapp.contato.persistencia.ContatoDAO;
import com.heyfood.heyfoodapp.endereco.persistencia.EnderecoDAO;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.pessoa.persistencia.PessoaDAO;
import com.heyfood.heyfoodapp.usuario.dominio.Usuario;
import com.heyfood.heyfoodapp.usuario.persistencia.UsuarioDAO;

public class UsuarioServices {
    private UsuarioDAO usuarioDAO;
    private PessoaDAO pessoaDAO;
    private ContatoDAO contatoDAO;
    private EnderecoDAO enderecoDAO;

    public UsuarioServices(Context context){
        usuarioDAO = new UsuarioDAO(context);
        pessoaDAO = new PessoaDAO(context);
        contatoDAO = new ContatoDAO(context);
        enderecoDAO = new EnderecoDAO(context);
    }

    public void cadastrar(Usuario usuario) throws Exception {
        if (usuarioDAO.getUsuario(usuario.getLogin()) != null) {
            throw new Exception();
        }
        long idContato = contatoDAO.cadastrar(usuario.getPessoa().getContato());
        usuario.getPessoa().getContato().setId(idContato);

        long idEndereco = enderecoDAO.cadastrar(usuario.getPessoa().getEndereco());
        usuario.getPessoa().getEndereco().setId(idEndereco);

        long idPessoa = pessoaDAO.cadastrar(usuario.getPessoa());
        usuario.getPessoa().setId(idPessoa);

        long idUsuario = usuarioDAO.cadastrar(usuario);
        usuario.setId(idUsuario);
    }

    public void trocarSenha(Usuario usuario, String senhaAtual, String senhaNova) throws Exception{
        if(!usuario.getSenha().equals(senhaAtual)){
            throw new Exception();
        }
        usuario.setSenha(senhaNova);
        usuarioDAO.updateUsuario(usuario);
    }

    public void login(String email, String password) throws Exception {
        Usuario usuario = usuarioDAO.getUsuario(email, password);
        if (usuario == null) {
            throw new Exception();
        }
        Sessao.instance.setUsuario(usuario);
    }

    public void logout() {
        Sessao sessao = Sessao.instance;
        sessao.reset();
    }
}
