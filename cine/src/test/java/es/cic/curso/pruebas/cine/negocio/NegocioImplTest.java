package es.cic.curso.pruebas.cine.negocio;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.pruebas.cine.excepciones.CineException;
import es.cic.curso.pruebas.cine.excepciones.NoHayMasEntradasException;
import es.cic.curso.pruebas.cine.repository.sesion.Sesion;
import es.cic.curso.pruebas.cine.repository.venta.Venta;
import es.cic.curso.pruebas.cine.service.negocio.NegocioService;
import es.cic.curso.pruebas.cine.service.sala.SalaService;
import es.cic.curso.pruebas.cine.service.sesion.SesionService;
import es.cic.curso.pruebas.cine.service.ventas.VentasService;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/pruebas/cine/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class NegocioImplTest implements GeneraraCine {
	
	@Autowired
	SesionService sesionService;
	
	@Autowired
	SalaService salaService;
	
	@Autowired
	VentasService ventaService;
	
	@Autowired
	NegocioService negocioService;
	
	private Long[]idSesiones;

	@Before
	public void setUp() throws Exception {
		
		idSesiones = generarCine();
	}
	
	@Test
	public void testVender_si() throws CineException {
		//
		Long idVenta1 = negocioService.vender(idSesiones[0], 100);
		Long idVenta2 = negocioService.vender(idSesiones[3], 50);
		Long idVenta3 = negocioService.vender(idSesiones[6], 30);
		
		assertNotNull(idVenta1);
		assertNotNull(idVenta2);
		assertNotNull(idVenta3);
	}
	
	@Test(expected = NoHayMasEntradasException.class)
	public void testVender_no_Sala1_Sesion1() throws CineException {
		negocioService.vender(idSesiones[0], 101);	
	}
	
	@Test(expected = NoHayMasEntradasException.class)
	public void testVender_no_Sala2_Sesion1() throws CineException {
		negocioService.vender(idSesiones[3], 51);	
	}

	@Test(expected = NoHayMasEntradasException.class)
	public void testVender_no_Sala3_Sesion1() throws CineException {
		negocioService.vender(idSesiones[6], 31);	
	}
	
	@Test
	public void testCalcularImporte_sinDescuento() {
		int numEntradas = 4;
		double importe = negocioService.calcularImporte(numEntradas);
		
		assertTrue(importe == 22);
	}
	
	@Test
	public void testCalcularImporte_conDescuento() {
		int numEntradas = 5;
		double importe = negocioService.calcularImporte(numEntradas);
		
		assertTrue(importe == 24.75);
	}

	@Test
	public void testHayEntradas_si() {
		boolean hayEntradas = negocioService.hayEntradas(idSesiones[0], 100);
		
		assertTrue(hayEntradas);
	}

	@Test
	public void testHayEntradas_no() {
		boolean hayEntradas = negocioService.hayEntradas(idSesiones[0], 101);
		
		assertTrue(!hayEntradas);
	}
	
	@Test
	public void testCerrarSesion() {
		negocioService.cerrarSesion(idSesiones[0]);
		sesionService.obtenerSesion(idSesiones[0]);
		Sesion sesionMod = sesionService.obtenerSesion(idSesiones[0]);
		assertTrue(sesionMod.isEstaCerrada());
		
	}
	
	@Test
	public void testVenderSesionCerrada() {
		negocioService.cerrarSesion(idSesiones[0]);
		
		Sesion sesion = sesionService.obtenerSesion(idSesiones[0]);
		
		assertTrue(sesion.isEstaCerrada());
		
	}
	
	@Test
	public void testcambiarEntradas() throws CineException {
		Long idVenta1 = negocioService.vender(idSesiones[0], 20);
		
		Venta venta = negocioService.cambiarEntradas(idVenta1, idSesiones[3]);
		
		Sesion sesionNueva =sesionService.obtenerSesion(venta.getSesion().getId());
		Sesion sesion = sesionService.obtenerSesion(idSesiones[0]);
		
		assertTrue(sesion.getAsientosOcupados() == 0);
		assertTrue(sesionNueva.getAsientosOcupados() == 20);
	}
	
	@Test
	public void testDevolverEntradas() throws CineException {
		Long idVenta1 = negocioService.vender(idSesiones[0], 20);
		negocioService.devolverEntradas(idVenta1);
		
		Venta venta = ventaService.obtenerVenta(idVenta1);
		
		assertNull(venta);
	}
	
	@Test
	public void testRecaudarSala() throws CineException {
		Long idVenta1 = negocioService.vender(idSesiones[0], 10);
		Long idVenta2 = negocioService.vender(idSesiones[0], 10);
		Long idVenta3 = negocioService.vender(idSesiones[0], 10);
		
		Venta venta = ventaService.obtenerVenta(idVenta1);
		
		
		double recaudacion = negocioService.recaudarSala(venta.getSala().getId());
		
		assertTrue(recaudacion == 148.50);
	}
	
	@Test
	public void testRecaudarCine() throws CineException {
		Long idVenta1 = negocioService.vender(idSesiones[0], 10);
		Long idVenta2 = negocioService.vender(idSesiones[3], 10);
		Long idVenta3 = negocioService.vender(idSesiones[6], 10);
		
		Venta venta = ventaService.obtenerVenta(idVenta1);
		
		
		double recaudacion = negocioService.recaudarCine();
		
		assertTrue(recaudacion == 148.50);
	}
	
	@Test
	public void testVerSitiosaLibres() throws CineException {
		Long idVenta1 = negocioService.vender(idSesiones[0], 10);
		int sitiosLibres = negocioService.verSitiosLibres(idSesiones[0]);
		assertTrue(sitiosLibres == 90);
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.pruebas.cine.negocio.GeneraraCine#generarCine()
	 */
	@Override
	public Long[] generarCine(){
		
		//Generamos las salas
		
		Long idSala1 = salaService.aniadirSala(100);
		Long idSala2 = salaService.aniadirSala(50);
		Long idSala3 = salaService.aniadirSala(30);
		
		//Generamos las sesiones
		
		Long idSesion11 = sesionService.aniadirSesion(0, false, idSala1);
		Long idSesion12 = sesionService.aniadirSesion(0, false, idSala1);
		Long idSesion13 = sesionService.aniadirSesion(0, false, idSala1);
		
		Long idSesion21 = sesionService.aniadirSesion(0, false, idSala2);
		Long idSesion22 = sesionService.aniadirSesion(0, false, idSala2);
		Long idSesion23 = sesionService.aniadirSesion(0, false, idSala2);
		
		Long idSesion31 = sesionService.aniadirSesion(0, false, idSala3);
		Long idSesion32 = sesionService.aniadirSesion(0, false, idSala3);
		Long idSesion33 = sesionService.aniadirSesion(0, false, idSala3);
		
		//Guardamos las sesiones
		
		Long[]idSesiones = {idSesion11, idSesion12, idSesion13,
							idSesion21, idSesion22, idSesion23,
							idSesion31, idSesion32, idSesion33,};
		
		return idSesiones;
	}

	@Test
	public void test() {
		assertTrue(true);
	}

}
