package br.estacio.adotapet.backend.service;

import br.estacio.adotapet.backend.config.service.JwtService;
import br.estacio.adotapet.backend.dto.in.UsuarioCreateDto;
import br.estacio.adotapet.backend.dto.in.UsuarioUpdateDto;
import br.estacio.adotapet.backend.dto.out.TokenAutenticacaoDto;
import br.estacio.adotapet.backend.dto.out.UsuarioCadastradoDto;
import br.estacio.adotapet.backend.exception.ExcecaoApi;
import br.estacio.adotapet.backend.exception.ExcecaoApiEnum;
import br.estacio.adotapet.backend.model.TbUsuario;
import br.estacio.adotapet.backend.repository.TbUsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Contém a lógica relacionada ao CRUD de usuários.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class UsuarioService {

    private final TbUsuarioRepository tbUsuarioRepository;
    private final JwtService jwtService;

    public TokenAutenticacaoDto create(@NonNull UsuarioCreateDto dto) {

        try {

            TbUsuario usuario = new TbUsuario();
            usuario.setNome(dto.nome());
            usuario.setEmail(dto.email());
            usuario.setSenha(dto.senha());
            usuario.setCpf(dto.cpf());
            usuario.setTelefone(dto.telefone());

            // Após criar o novo usuário no banco, retorna seu token de autenticação.
            return new TokenAutenticacaoDto(
                    jwtService.geraToken(
                            tbUsuarioRepository.save(usuario).getEmail()));
        } catch (Exception e) {
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaAoCriarUsuario);
        }
    }

    public UsuarioCadastradoDto read() {

        // Busca o usuário autenticado.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verifica se o usuário autenticado é um usuário cadastrado no banco.
        // Retorna o DTO, caso seja um usuário cadastrado.
        // Lança exceção, caso o usuário não esteja cadastrado no banco.
        if (userDetails instanceof TbUsuario) {
            return new UsuarioCadastradoDto(
                    ((TbUsuario) userDetails).getId(),
                    ((TbUsuario) userDetails).getNome(),
                    ((TbUsuario) userDetails).getEmail(),
                    ((TbUsuario) userDetails).getCpf(),
                    ((TbUsuario) userDetails).getTelefone(),
                    ((TbUsuario) userDetails).getDtCriacao());
        } else
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaBuscaDadosUsuario);
    }

    @Transactional
    public TokenAutenticacaoDto update(@NonNull UsuarioUpdateDto dto) {

        TbUsuario usuario = tbUsuarioRepository.findById(dto.id())
                .orElseThrow(() -> new ExcecaoApi(ExcecaoApiEnum.FalhaBuscaDadosUsuario));

        try {

            // Verifica onde houve atualização dos dados.
            if (dto.nome() != null && !dto.nome().isBlank() && !dto.nome().equals(usuario.getNome()))
                usuario.setNome(dto.nome());
            if (dto.email() != null && !dto.email().isBlank() && !dto.email().equals(usuario.getEmail()))
                usuario.setEmail(dto.email());
            if (dto.telefone() != null && !dto.telefone().isBlank() && !dto.telefone().equals(usuario.getTelefone()))
                usuario.setTelefone(dto.telefone());

            // Após criar o novo usuário no banco, retorna seu token de autenticação.
            return new TokenAutenticacaoDto(
                    jwtService.geraToken(usuario.getEmail()));
        } catch (Exception e) {
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaAoAtualizarUsuario);
        }
    }

    @Transactional
    public String delete(Long id) {

        TbUsuario usuario = tbUsuarioRepository.findById(id)
                .orElseThrow(() -> new ExcecaoApi(ExcecaoApiEnum.FalhaBuscaDadosUsuario));

        try {

            usuario.setHabilitado(false);

            return "Usuário excluído com sucesso!";
        } catch (Exception e) {
            throw new ExcecaoApi(ExcecaoApiEnum.FalhaAoExcluirrUsuario);
        }
    }
}
