package inventario_api.service;

import inventario_api.model.Producto;
import inventario_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto producto = buscarPorId(id);
        producto.setNombre(productoActualizado.getNombre());
        producto.setSku(productoActualizado.getSku());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        productoRepository.delete(producto);
    }

    public Producto descontarStock(Long id, Integer cantidad) {
        Producto producto = buscarPorId(id);
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStock());
        }
        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }
}
