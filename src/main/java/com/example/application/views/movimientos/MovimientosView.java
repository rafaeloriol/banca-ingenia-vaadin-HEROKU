package com.example.application.views.movimientos;

import com.example.application.backend.modelbanca.Movimiento;
import com.example.application.backend.modelbanca.Usuario;
import com.example.application.backend.servicebanca.MovimientoService;
import com.example.application.backend.servicebanca.impl.AuthService;
import com.example.application.views.Uti;
import com.example.application.views.tarjetas.TarjetasView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.List;

//@Route(value = "movimientos", layout = MainView.class)
@PageTitle("Bienvenido/a a tu banca")
public class MovimientosView extends VerticalLayout {

    private final MovimientoService movimientoService;
    private final AuthService authService;
    private PaginatedGrid<Movimiento> movimientoGrid = new PaginatedGrid<>(Movimiento.class);
    private List<Movimiento> movimientoList;
    private ListDataProvider<Movimiento>movimientoListDataProvider;

    public MovimientosView(MovimientoService movimientoService, AuthService authService){

//        this.setSizeFull();
        this.setWidth(Uti.ANCHO);
        this.setPadding(true);

        this.authService = authService;
        this.movimientoService = movimientoService;

        loadData();

        configureGrid();
    }

    private void loadData(){
        try{
            Usuario usuarioLogeado = authService.recuperaUsuarioLogeado();
            this.movimientoList = movimientoService.recuperaMovimientosPorIdUsuario(usuarioLogeado.getId());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void loadGrid(){
        movimientoListDataProvider = DataProvider.ofCollection(this.movimientoList);
        movimientoListDataProvider.setSortOrder(Movimiento::getFechaOperacion, SortDirection.DESCENDING);

        movimientoGrid.setDataProvider(movimientoListDataProvider);

    }

    private void configureGrid(){
        loadGrid();
//        movimientoGrid.setSizeFull();
        movimientoGrid.setWidth(Uti.ANCHO);

        movimientoGrid.setColumns("numTarjeta");
        movimientoGrid.getColumnByKey("numTarjeta").setHeader("Nº Tarjeta").setVisible(false);

        movimientoGrid.addComponentColumn(item ->{
            Icon icon;
            if(item.getCantidad() < 0.0){
                icon = VaadinIcon.CHEVRON_CIRCLE_DOWN_O.create();
                icon.setColor("red");
            }else{
                icon = VaadinIcon.CHEVRON_CIRCLE_UP_O.create();
                icon.setColor("green");
            }
            return icon;
        }).setKey("icon").setHeader("").setFlexGrow(0).setWidth("60px");
        movimientoGrid.addColumn(item -> TarjetasView.tarjetaUltimosDigitos(item.getNumTarjeta())).setHeader("Nº Tarjeta").setSortable(true);
        movimientoGrid.addColumn(Movimiento::getCantidad).setHeader("Cantidad").setSortable(true);
        movimientoGrid.addColumn(Movimiento::getConcepto).setHeader("Concepto").setSortable(true);
        movimientoGrid.addColumn(Movimiento::getFechaOperacion).setHeader("Fecha").setSortable(true);

        //Paginator
        movimientoGrid.setPageSize(10);
        movimientoGrid.setPaginatorSize(5);

        movimientoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);

        add(createTitle(), movimientoGrid);
    }

    private Component createTitle() {
        return new H3("Movimientos");
    }
}
