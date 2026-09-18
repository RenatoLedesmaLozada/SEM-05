package com.electrostore.pedidos.repository;

import com.electrostore.pedidos.entity.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductoCustomRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Búsqueda avanzada usando EntityManager y JPQL parametrizado de forma segura.
	 */
	public List<Producto> buscarPorFiltrosAvanzados(String nombreKeyword, Integer stockMinimo) {
		String jpql = "SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(:keyword) AND p.stock >= :stockMin";

		TypedQuery<Producto> query = entityManager.createQuery(jpql, Producto.class);
		query.setParameter("keyword", "%" + nombreKeyword + "%");
		query.setParameter("stockMin", stockMinimo);

		return query.getResultList();
	}

	/**
	 * Reto 2: Búsqueda de productos con stock inferior a un umbral dado (Alerta de
	 * Stock Bajo).
	 */
	public List<Producto> buscarProductosConStockBajo(Integer umbralStock) {
		String jpql = "SELECT p FROM Producto p WHERE p.stock < :umbral ORDER BY p.stock ASC";

		TypedQuery<Producto> query = entityManager.createQuery(jpql, Producto.class);
		query.setParameter("umbral", umbralStock);

		return query.getResultList();
	}
}