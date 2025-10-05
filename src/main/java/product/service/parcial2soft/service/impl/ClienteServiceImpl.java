package product.service.parcial2soft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product.service.parcial2soft.dto.ClienteCreateDTO;
import product.service.parcial2soft.dto.ClienteResponseDTO;
import product.service.parcial2soft.dto.ClienteUpdateDTO;
import product.service.parcial2soft.entity.Cliente;
import product.service.parcial2soft.exceptions.ClienteExitsException;
import product.service.parcial2soft.exceptions.ClienteNotFoundException;
import product.service.parcial2soft.mapper.ClienteMapper;
import product.service.parcial2soft.repository.ClienteRepository;
import product.service.parcial2soft.service.interfaces.ClienteService;
import product.service.parcial2soft.util.PasswordEncoderUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoderUtil passwordEncoderUtil;

    @Override
    public ClienteResponseDTO createCliente(ClienteCreateDTO dto) {
        log.info("Creando nuevo cliente con documento: {}", dto.email());

        // Validar que no exista un cliente con el email
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new ClienteExitsException("Ya existe un cliente con el email: " + dto.email());
        }

        ClienteCreateDTO clienteEncrypted = hashPassword(dto);

        Cliente cliente = clienteMapper.toEntity(clienteEncrypted);
        Cliente savedCliente = clienteRepository.save(cliente);

        log.info("Cliente creado exitosamente con ID: {}", savedCliente.getId());
        return clienteMapper.toResponseDTO(savedCliente);
    }


    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO getClienteById(Long id) {
        log.info("Buscando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        return clienteMapper.toResponseDTO(cliente);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getAllClientes() {
        log.info("Obteniendo todos los clientes");

        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toResponseDTOList(clientes);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getClientesActivos() {
        log.info("Obteniendo clientes activos");

        List<Cliente> clientes = clienteRepository.findByActivoTrue();
        return clienteMapper.toResponseDTOList(clientes);
    }


    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO getClienteByEmail(String email) {
        log.info("Buscando cliente con documento: {}", email);

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con email: " + email));

        return clienteMapper.toResponseDTO(cliente);
    }


    @Override
    public ClienteResponseDTO updateCliente(Long id, ClienteUpdateDTO dto) {
        log.info("Actualizando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        // Validar que no exista un cliente con el email
        if (clienteRepository.existsByEmail(dto.email()) && !cliente.getEmail().equals(dto.email())) {
            throw new ClienteExitsException("Ya existe un cliente con el email: " + dto.email());
        }

        // Actualizar los campos usando MapStruct
        clienteMapper.updateEntityFromDTO(dto, cliente);

        Cliente updatedCliente = clienteRepository.save(cliente);

        log.info("Cliente actualizado exitosamente con ID: {}", updatedCliente.getId());
        return clienteMapper.toResponseDTO(updatedCliente);
    }


    @Override
    public void deleteCliente(Long id) {
        log.info("Desactivando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        cliente.setActivo(false);
        clienteRepository.save(cliente);

        log.info("Cliente desactivado exitosamente con ID: {}", id);
    }


    @Override
    public void deleteClientePermanente(Long id) {
        log.info("Eliminando permanentemente cliente con ID: {}", id);

        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        }

        clienteRepository.deleteById(id);

        log.info("Cliente eliminado permanentemente con ID: {}", id);
    }


    private ClienteCreateDTO hashPassword(ClienteCreateDTO dto) {

        String encryptedPassword = passwordEncoderUtil.encryptPassword(dto.password());

        // Crear el DTO con la contraseña encriptada
        return new ClienteCreateDTO(
                dto.nombre(),
                dto.apellido(),
                encryptedPassword, // Contraseña encriptada
                dto.email(),
                dto.telefono(),
                dto.direccion()
        );
    }
}
