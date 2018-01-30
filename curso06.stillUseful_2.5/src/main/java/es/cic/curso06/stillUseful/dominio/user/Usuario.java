package es.cic.curso06.stillUseful.dominio.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import es.cic.curso06.stillUseful.dominio.producto.Producto;
import es.cic.curso06.stillUseful.repository.abstracto.Identificable;
@Entity
public class Usuario implements Identificable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5343964396560995793L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="NOMBRE")
	private String nombre;
 
	@Column(name="APELLIDOS")
	private String apellidos;
	
	@Column(name="DNI")
	private String dni;

	@Column(name="CALLE")
	private String calle;
	
	@Column(name="CIUDAD")
	private String ciudad;
	
	@Column(name="PROVINCIA")
	private String provincia;

	@Column(name="COMUNIDAD")
	private String comunidad;
	
	@Column(name="CODIGO_POSTAL")
	private int codigoPostal;

	@Column(name="EMAIL")
	private String email;
	
	@OneToMany(mappedBy="usuarioId") 
	private List<Producto> listaProductos = new ArrayList<>();




	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String apellidos, String dni, String calle, String ciudad, String provincia,
			String comunidad, int codigoPostal, String email) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.calle = calle;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.comunidad = comunidad;
		this.codigoPostal = codigoPostal;
		this.email = email;
	}

	public Usuario(Long id, String nombre, String apellidos, String dni, String calle, String ciudad, String provincia,
			String comunidad, int codigoPostal, String email) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.calle = calle;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.comunidad = comunidad;
		this.codigoPostal = codigoPostal;
		this.email = email;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", calle="
				+ calle + ", ciudad=" + ciudad + ", provincia=" + provincia + ", comunidad=" + comunidad
				+ ", codigoPostal=" + codigoPostal + ", email=" + email + "]";
	}
	
	
}
