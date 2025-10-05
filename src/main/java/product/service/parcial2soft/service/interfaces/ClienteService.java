package product.service.parcial2soft.service.interfaces;


import product.service.parcial2soft.dto.ClienteCreateDTO;
import product.service.parcial2soft.dto.ClienteResponseDTO;
import product.service.parcial2soft.dto.ClienteUpdateDTO;

import java.util.List;

public interface ClienteService {

    /**
     * Crear un nuevo cliente
     * @param dto Datos del cliente a crear
     * @return Cliente creado
     */
    ClienteResponseDTO createCliente(ClienteCreateDTO dto);

    /**
     * Obtener un cliente por su ID
     * @param id ID del cliente
     * @return Cliente encontrado
     */
    ClienteResponseDTO getClienteById(Long id);

    /**
     * Obtener todos los clientes
     * @return Lista de todos los clientes
     */
    List<ClienteResponseDTO> getAllClientes();

    /**
     * Obtener solo clientes activos
     * @return Lista de clientes activos
     */
    List<ClienteResponseDTO> getClientesActivos();

    /**
     * Buscar cliente por documento
     * @return Cliente encontrado
     */
    ClienteResponseDTO getClienteByEmail(String email);

    /**
     * Actualizar un cliente existente
     * @param id ID del cliente a actualizar
     * @param dto Datos actualizados
     * @return Cliente actualizado
     */
    ClienteResponseDTO updateCliente(Long id, ClienteUpdateDTO dto);

    /**
     * Eliminar un cliente (borrado lógico)
     * @param id ID del cliente a eliminar
     */
    void deleteCliente(Long id);

    /**
     * Eliminar permanentemente un cliente (borrado físico)
     * @param id ID del cliente a eliminar
     */
    void deleteClientePermanente(Long id);
}
