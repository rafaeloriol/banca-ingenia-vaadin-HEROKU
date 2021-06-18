package com.example.application.views.prestamo;

import com.example.application.backend.modelbanca.Cuenta;
import com.example.application.backend.modelbanca.Movimiento;
import com.example.application.backend.modelbanca.Prestamo;
import com.example.application.backend.modelbanca.Usuario;
import com.example.application.backend.servicebanca.CategoriaService;
import com.example.application.backend.servicebanca.CuentaService;
import com.example.application.backend.servicebanca.MovimientoService;
import com.example.application.backend.servicebanca.PrestamoService;
import com.example.application.backend.servicebanca.impl.AuthService;
import com.example.application.views.Uti;
import com.example.application.views.inicio.InicioView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;

import java.time.LocalDate;
import java.util.List;

@PageTitle("Préstamo")
public class PrestamoView extends VerticalLayout {

    private final PrestamoService prestamoService;
    private final AuthService authService;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;
    private final CategoriaService categoriaService;

    private Prestamo prestamo = new Prestamo();
    private Binder<Prestamo>prestamoBinder = new BeanValidationBinder<>(Prestamo.class);

    private FormLayout formLayout;

    private NumberField cantidad;
    private NumberField duracion;
    private NumberField tipoInteres;
    private ComboBox<Cuenta> cuentaIngreso;
    private ComboBox<Cuenta> cuentaCobro;

    public PrestamoView(PrestamoService prestamoService, AuthService authService, CuentaService cuentaService, MovimientoService movimientoService, CategoriaService categoriaService){
        this.prestamoService = prestamoService;
        this.authService = authService;
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
        this.categoriaService = categoriaService;

        add(createTitle(),createFormLayout(), new Hr(), createToolbarLayout());

        prestamoBinder.bindInstanceFields(this);

        setWidth("800px");
    }

    public void setPrestamo(Prestamo prestamo){
        this.prestamo = prestamo;
        prestamoBinder.readBean(prestamo);
    }

    public Prestamo getPrestamo(){
        return this.prestamo;
    }

    public List<Cuenta> getCuentas(){
        Usuario usuario = authService.recuperaUsuarioLogeado();
        List<Cuenta>cuentas = cuentaService.encuentraCuentasDeUsuario(usuario.getId());
                return cuentas;
    }

    private Component createTitle(){
        return new H3("Solicita tu Préstamo");
    }

    private Component createFormLayout(){
        formLayout = new FormLayout();
        formLayout.setWidthFull();

        cuentaIngreso = new ComboBox<Cuenta>();
        cuentaIngreso.setId("cuentaIngreso");
        cuentaIngreso.setItemLabelGenerator(Cuenta::getEntidad);
        cuentaIngreso.setLabel("Cuenta de Ingreso");
        cuentaIngreso.setItems(getCuentas());
        prestamoBinder.forField(cuentaIngreso);

        cuentaCobro = new ComboBox<Cuenta>();
        cuentaCobro.setId("cuentaCobro");
        cuentaCobro.setItemLabelGenerator(Cuenta::getEntidad);
        cuentaCobro.setLabel("Cuenta de Cobro");
        cuentaCobro.setItems(getCuentas());
        prestamoBinder.forField(cuentaCobro);

        cantidad = new NumberField();
        cantidad.setId("cantidad");
        cantidad.setLabel("Cantidad");
        cantidad.setSuffixComponent(new Icon(VaadinIcon.EURO));
        cantidad.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        prestamoBinder.forField(cantidad);

        duracion = new NumberField();
        duracion.setId("duracion");
        duracion.setLabel("Duración en meses");
        duracion.setSuffixComponent(new Icon(VaadinIcon.TIMER));
        duracion.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        prestamoBinder.forField(duracion);

        tipoInteres = new NumberField();
        tipoInteres.setId("interes");
        tipoInteres.setLabel("Interés");
        tipoInteres.setSuffixComponent(new Icon(VaadinIcon.BOOK_PERCENT));
        tipoInteres.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        prestamoBinder.forField(tipoInteres);

        formLayout.add(cuentaIngreso, cuentaCobro, cantidad, duracion, tipoInteres);
        return formLayout;
    }

    private Component createToolbarLayout(){
        Button saveButton = new Button("Confirmar");
        saveButton.addClickListener(clickEvent -> {
            prestamoBinder.writeBeanIfValid(getPrestamo());

            crearPrestamo(getPrestamo());

        });

        Button cancelButton = new Button("Cancelar");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(clickEvent -> {
            UI.getCurrent().getPage().reload();
        });

        HorizontalLayout formToolBar = new HorizontalLayout(saveButton, cancelButton);
        formToolBar.setWidthFull();

        return formToolBar;
    }


    public class CobroPrestamoHilo extends Thread{


        Double duracion;
        Double cantidad;
        Double tipoInteres;
        Cuenta cuentaCobro;
        int tiempoIntervaloSimulacion; // en Segundos
        Long idPrestamo;

        /**
         *
         * @param duracion en meses
         * @param cantidad total del préstamo
         * @param tipoInteres % de intereses
         * @param cuentaCobro la cuenta donde se van a cobrar las cuotas
         * @param tiempoIntervaloSimulacion en segundos
         * @param idPrestamo
         */
        public CobroPrestamoHilo(Double duracion, Double cantidad, Double tipoInteres, Cuenta cuentaCobro, int tiempoIntervaloSimulacion, Long idPrestamo) {
            this.duracion = duracion;
            this.cantidad = cantidad;
            this.tipoInteres = tipoInteres;
            this.cuentaCobro = cuentaCobro;
            this.tiempoIntervaloSimulacion = tiempoIntervaloSimulacion * 1000;
            this.idPrestamo = idPrestamo;

        }

        public void run(){

            while(Uti.hilo_trabajando == true){
                try {
                    System.out.println("Esperando(idPrestamo " + idPrestamo +  ")... -> Hilo esperando: " + Uti.hilo_trabajando);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Se informa de que el hilo ha empezado a trabajar
            Uti.hilo_trabajando = true;
            System.out.println("(idPrestamo " + idPrestamo +  ")... -> Hilo iniciado (true)");

            Double cuota = 0d;
            for (int i = 0; i < duracion; i++) {

                System.out.println("(idPrestamo " + idPrestamo +  ")... -> Hilo trabajando: iteración = " + i );

                Movimiento movimiento = new Movimiento();
                movimiento.setNumTarjeta("");

                cuota = calculoCuotaPrestamo(cantidad, tipoInteres, duracion, i + 1);

                cuentaCobro = cuentaService.recuperarCuentaPorId(cuentaCobro.getId()).get();
                movimiento.setCuenta(cuentaCobro);
                movimiento.setCategoria(categoriaService.encuentraPorTipoCategoria("Préstamo"));
                movimiento.setFechaValor(LocalDate.now());
                movimiento.setFechaOperacion(LocalDate.now());

                movimiento.setConcepto("Préstamo (id:" + idPrestamo + ") " + (i+1) + "/" + Math.round(duracion));
                movimiento.setCantidad(-1*cuota);
                movimiento.setSaldoActual(cuentaCobro.getSaldo() - cuota);

                // todo -- detener la ejecución por un tiempo
                try{
                    Thread.sleep(this.tiempoIntervaloSimulacion);
                } catch(InterruptedException e ) {
                    System.out.println("Thread Interrupted");
                }


                // Persistimos en BBDD
                movimientoService.creaMovimiento(movimiento);

                // Actualizamos el saldo de la cuenta
                cuentaCobro.setSaldo(cuentaCobro.getSaldo() - cuota);
                cuentaService.actualizaCuenta(cuentaCobro);

            }

            // Se informa de que el hilo ha parado
            Uti.hilo_trabajando = false;
            System.out.println("Esperando(idPrestamo " + idPrestamo +  ")... -> Hilo parado(false)   ");
        }
    }

    private void crearPrestamo(Prestamo prestamo) {

        Notification.show("Préstamo concedido", 3000, Notification.Position.MIDDLE);
        UI.getCurrent().navigate(InicioView.class);

        Double cantidad = prestamo.getCantidad();
        Double duracion = prestamo.getDuracion();
        Double tipoInteres = prestamo.getTipoInteres();
        Cuenta cuentaIngreso = prestamo.getCuentaIngreso();
        Cuenta cuentaCobro = prestamo.getCuentaCobro();

        // CREACIÓN DE PRESTAMO
        // Persistimos el prestamo en BBDD
        prestamoService.createPrestamo(prestamo);

        // SIMULACIÓN DE LA EJECUCIÓN DE PRESTAMO
        // Se crea un movimiento en la cuenta de INGRESO de la cantidad del prestamo,
        Movimiento movimientoIngreso = new Movimiento();
        movimientoIngreso.setCantidad(cantidad);

        movimientoIngreso.setCategoria(categoriaService.encuentraPorTipoCategoria("Préstamo"));
        movimientoIngreso.setConcepto("Ingreso Préstamo");
        movimientoIngreso.setNumTarjeta("");
        movimientoIngreso.setCuenta(cuentaIngreso);
        movimientoIngreso.setFechaOperacion(LocalDate.now());
        movimientoIngreso.setFechaValor(LocalDate.now());
        movimientoIngreso.setSaldoActual(cuentaIngreso.getSaldo() + cantidad);

        // Persistimos el movimiento en BBDD
        movimientoService.creaMovimiento(movimientoIngreso);

        // se actualiza el saldo de la cuenta INGRESO y persiste la cuenta
        cuentaIngreso.setSaldo(cuentaIngreso.getSaldo() + cantidad);
        cuentaService.actualizaCuenta(cuentaIngreso);


        // Se realizan los movimientos correspondientes en la cuenta de COBRO

        // Calculo de la cuota mensual del préstamo

        // SIMULACIÓN DE COBRO EN LA CUENTA
        CobroPrestamoHilo hilo = new CobroPrestamoHilo(duracion, cantidad, tipoInteres, cuentaCobro, 10, prestamo.getId());
        hilo.start();



    }

    private Double calculoCuotaPrestamo(Double cantidad, Double tipoInteres, Double duracion, int numCuota) {
        // todo ...
        // a = C0/(1-   (1/(1+i))n)  /i)

//        Double a = 0d;
//        Double C0 = cantidad;
//        Double i = tipoInteres;
//        Double n = Double.valueOf(numCuota);
//
//        a = C0/(1-Math.pow(1/(1+i),n)/i);

        return 100d;
    }

}
