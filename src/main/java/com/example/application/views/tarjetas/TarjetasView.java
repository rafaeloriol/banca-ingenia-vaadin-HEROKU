package com.example.application.views.tarjetas;

import com.example.application.backend.modelbanca.Tarjeta;
import com.example.application.backend.modelbanca.Usuario;
import com.example.application.backend.servicebanca.CuentaService;
import com.example.application.backend.servicebanca.MovimientoService;
import com.example.application.backend.servicebanca.TarjetaService;
import com.example.application.backend.servicebanca.impl.AuthService;
import com.example.application.views.Uti;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
//import io.oferto.application.components.AuthenticatedButton;
//import io.oferto.application.security.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//@Route(value = "tarjetas", layout = MainView.class)
@PageTitle("Bienvenido/a a tu banca")
public class TarjetasView extends VerticalLayout {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Grid<Tarjeta> gridTarjeta = new Grid<>(Tarjeta.class);
    private ListDataProvider<Tarjeta> tarjetaProvider;
    private List<Tarjeta> tarjetas;

    // Inyección del servicio TarjetaService
    private final TarjetaService tarjetaService;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;
    private final AuthService authService;

    public TarjetasView(TarjetaService tarjetaService, CuentaService cuentaService, MovimientoService movimientoService, AuthService authService){
        this.tarjetaService = tarjetaService;
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
        this.authService = authService;


//        addClassName("about-view");
//        setSizeFull();
        this.setWidth(Uti.ANCHO);
        setPadding(true);


        // Botón para crear una nueva Tarjeta
//        Button btnCrearTarjeta = new Button("Crear Tarjeta");
//        btnCrearTarjeta.addClickListener(event ->{
//            TarjetaForm formularioTarjeta = new TarjetaForm(null, tarjetaService, this.cuentaService);
//            formularioTarjeta.open();
//            formularioTarjeta.addOpenedChangeListener(event1 -> {
//                loadData();
//                loadGrid();
//            });
//        });

//        this.add(btnCrearTarjeta, new Hr());



        // load data from service
        loadData();

        // fill grid with data
        configureGrid();

        // create view layput
        add(createTitle(), gridTarjeta);

    }



    private void loadData() {
        try {

            // todo -- recuperar las tarjetas del usuario logeado

            // recupera usuarioLogeado
            Usuario usuarioLogeado = authService.recuperaUsuarioLogeado();

//            this.tarjetas = tarjetaService.encuentraTarjetas();
            this.tarjetas = tarjetaService.tarjetasUsuarioPorId(usuarioLogeado.getId());

        }
        catch(Exception ex) {
            ex.printStackTrace();

            logger.debug(ex.getLocalizedMessage());
        }
    }

    private void loadGrid() {
        tarjetaProvider =  DataProvider.ofCollection(this.tarjetas);
        tarjetaProvider.setSortOrder(Tarjeta::getTipoTarjeta, SortDirection.ASCENDING);

        gridTarjeta.setDataProvider(tarjetaProvider);
    }


    private void configureGrid() {
        loadGrid();

//        gridTarjeta.setSizeFull();
        gridTarjeta.setWidth(Uti.ANCHO);


        gridTarjeta.setColumns("numeroTarjeta");
        gridTarjeta.getColumnByKey("numeroTarjeta").setHeader("Numero tarjeta").setVisible(false);

        gridTarjeta.addColumn(tarjeta -> tarjetaUltimosDigitos(tarjeta.getNumeroTarjeta())).setHeader("Número tarjeta").setSortable(true);
        gridTarjeta.addColumn(item -> saldoPorTarjeta(gridTarjeta, item)).setHeader("Gasto Tarjeta").setSortable(true);
        gridTarjeta.addColumn(Tarjeta::getTipoTarjeta).setHeader("Tipo tarjeta").setSortable(true);
        gridTarjeta.addColumn(Tarjeta::getLimiteMaximo).setHeader("Límite máximo").setSortable(true);
        gridTarjeta.addColumn(tarjeta -> tarjeta.getCuenta().getNumeroCuenta()).setHeader("Número cuenta").setSortable(true);
        gridTarjeta.addColumn(tarjeta -> tarjeta.getCuenta().getEntidad()).setHeader("Entidad bancaria").setSortable(true);


//        gridTarjeta.getColumnByKey("tipoTarjeta").setFlexGrow(0).setWidth("200px").setHeader("Tipo tarjeta");
//        gridTarjeta.getColumnByKey("limiteMaximo").setFlexGrow(0).setWidth("200px").setHeader("Límite máximo");
//        gridTarjeta.getColumnByKey("cuenta.numeroCuenta").setFlexGrow(0).setWidth("200px").setHeader("Cuenta asociada");
//        gridTarjeta.getColumnByKey("cuenta.entidad").setFlexGrow(0).setWidth("200px").setHeader("Entidad bancaria");
//        gridTarjeta.getColumnByKey("fechaCaducidad").setFlexGrow(0).setWidth("200px").setHeader("Caducidad");
//        gridTarjeta.getColumnByKey("cvv").setFlexGrow(0).setWidth("200px").setHeader("CVV");



        // Se dibujan los botones 'Actualizar' y 'Borrar'
//        if (SecurityConfiguration.isAdmin()) {
//            gridTarjeta.addComponentColumn(item -> updateProductButton(gridTarjeta, item)).setFlexGrow(0).setWidth("120px").setHeader("");
//            gridTarjeta.addComponentColumn(item -> RemoveButton(gridTarjeta, item)).setFlexGrow(0).setWidth("120px").setHeader("");
//        }

        gridTarjeta.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);
    }

    public static String tarjetaUltimosDigitos(String numeroTarjeta) {
        if (numeroTarjeta.isEmpty()){
            return numeroTarjeta;
        }
        int size = numeroTarjeta.length();
        return "***" + numeroTarjeta.substring(size - 4);
    }

    private Object saldoPorTarjeta(Grid<Tarjeta> gridTarjeta, Tarjeta item) {
        return movimientoService.saldoTotalTarjeta(item.getNumeroTarjeta());
    }


//    private Component RemoveButton(Grid<Tarjeta> gridTarjeta, Tarjeta tarjeta) {
//        AuthenticatedButton button = new AuthenticatedButton("Borrar");
//        button.addClickListener(event -> {
//            tarjetaService.borrarTarjeta(tarjeta);
//            loadData();
//            loadGrid();
//        });
//        return button;
//
//    }


//    private Button updateProductButton(Grid<Tarjeta> grid, Tarjeta tarjeta) {
//        AuthenticatedButton button = new AuthenticatedButton("Actualizar");
//        button.addClickListener(event -> {
//            TarjetaForm formularioTarjeta = new TarjetaForm(tarjeta, tarjetaService, cuentaService);
//            formularioTarjeta.open();
//            formularioTarjeta.addOpenedChangeListener(event1 -> {
//                loadData();
//                loadGrid();
//            });
//        });
//
//
//
//
//
//        return button;
//    }

    private Component createTitle() {
        return new H3("Tarjetas");
    }

}
