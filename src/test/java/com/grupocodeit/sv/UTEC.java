package com.grupocodeit.sv;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.Assert;

public class UTEC {
	//Preparar la instancia del web driver
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		//asignar web driver a nuestro proyecto
		System.setProperty("webdriver.chrome.driver", "./src/test/chromedriver/chromedriver.exe"); 
		
		//istancia de la clase
		driver = new ChromeDriver();
		
		//maximizar la ventana del navegador
		driver.manage().window().maximize();
		
		//abrir la pagina web
		driver.get("https://portal.utec.edu.sv/");
	}

	@After
	public void tearDown() throws Exception {
		//cerrar el navegador
		driver.quit();
	}

	//Elementos mapeados
	By txtUser = By.id("UsuarioTxt");
	By txtPass = By.id("ContrasenaTxt");
	By btnLogin = By.name("BtnEntrar");
	
	By lblMensaje = By.id("msj");	
	String mensajeLoginIncorrecto="* Cuenta inactiva o ingreso mal su usuario o contraseña, intente nuevamente.";
	
	By tableError = By.id("ValidatorCalloutExtender2_popupTable");
	String mensajeDatosvacios = "El usuario es requerido!";
	
	@Test
	public void loginUtec() throws InterruptedException {
		LoginEmpty();
		loginError();
		loginSuccess();				
	}
	
	//Usuario y contraseñas vacios
	public void LoginEmpty() throws InterruptedException {
		driver.findElement(txtUser).sendKeys(" ");
		driver.findElement(txtPass).sendKeys(" ");
		driver.findElement(btnLogin).click();
		Thread.sleep(1000);
		if(driver.findElement(tableError).isDisplayed()) {			
			if(driver.findElement(tableError).getText().equals(mensajeDatosvacios)) {
				System.out.println("Exito: Usuario y contraseña vacíos");				
			}else {
				System.out.println("Fallo: el mensaje mostrado es diferente");
			}			
		}else {
			System.out.println("Falló: La tabla de validacion de los datos vacios no se motró");
		}		
	}
	
	//Usuario y contraseñas incorrectos
	public void loginError() throws InterruptedException {
		driver.findElement(txtUser).sendKeys("987654321");
		driver.findElement(txtPass).sendKeys("123456789");
		driver.findElement(btnLogin).click();
		Thread.sleep(1000);
		if(driver.findElement(lblMensaje).isDisplayed()) {			
			if(driver.findElement(lblMensaje).getText().equals(mensajeLoginIncorrecto)) {				
				assertEquals(mensajeLoginIncorrecto, driver.findElement(lblMensaje).getText());
				System.out.println("Exito: Usuario y/o contraseñas incorrectas");
			}else {
				assertEquals(mensajeLoginIncorrecto, driver.findElement(lblMensaje).getText());
				System.out.println("Fallo: mensaje incorrecto");
			}
			
		}else {
			System.out.println("Fallo: El Div no se mostró");
		}
	}
	
	//Usuario y contraseñas correctos
	public void loginSuccess() throws InterruptedException {
		driver.findElement(txtUser).sendKeys("USUARIO_CORRECTO");
		driver.findElement(txtPass).sendKeys("PASS_CORRECTA");
		driver.findElement(btnLogin).click();		
	}

}
