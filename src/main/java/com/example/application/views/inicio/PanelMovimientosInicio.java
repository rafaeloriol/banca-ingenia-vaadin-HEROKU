package com.example.application.views.inicio;

import com.example.application.backend.servicebanca.MovimientoService;
import com.example.application.views.Uti;
import com.example.application.views.movimientos.MovimientosView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.example.application.backend.modelbanca.Movimiento;
import com.example.application.backend.modelbanca.Usuario;
import com.example.application.backend.servicebanca.impl.AuthService;
import com.example.application.views.tarjetas.TarjetasView;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

public class PanelMovimientosInicio extends VerticalLayout {

    private final MovimientoService movimientoService;
    private final AuthService authService;
    private Grid<Movimiento> movimientoGrid = new Grid<>(Movimiento.class);
    private List<Movimiento> movimientoList;
    private ListDataProvider<Movimiento> movimientoListDataProvider;

    public PanelMovimientosInicio(MovimientoService movimientoService, AuthService authService) {
//        this.setSizeFull();
        this.setWidth(Uti.ANCHO_INICIO);
        this.setPadding(false);
        this.setMargin(false);

        this.authService = authService;
        this.movimientoService = movimientoService;

        loadData();

        HorizontalLayout toolBarLayout = new HorizontalLayout();
//        toolBarLayout.setWidthFull();
        toolBarLayout.setWidth(Uti.ANCHO_INICIO);
        H3 h3Movimientos = new H3("Movimientos");
        h3Movimientos.getElement().getStyle().set("margin-right", "auto");

        RouterLink routerLink = new RouterLink("Ver más", MovimientosView.class);
//        routerLink.getElement().getStyle().set("margin-top", "auto");

        toolBarLayout.add(h3Movimientos, routerLink);
        add(toolBarLayout);

        configureGrid();




    }

    private void loadData(){
        try{
            this.movimientoList = movimientosReducidos();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private List<Movimiento> movimientosReducidos(){

        Usuario usuarioLogeado = authService.recuperaUsuarioLogeado();
        this.movimientoList = movimientoService.recuperaMovimientosPorIdUsuario(usuarioLogeado.getId());

        List<Movimiento> movimientoResultado = new ArrayList<>();
        int limiteMax = 9;
        for (int i = movimientoList.size() - limiteMax; i < movimientoList.size(); i++) {
            movimientoResultado.add(movimientoList.get(i));
        }
        return movimientoResultado;
    }

    private void loadGrid(){
        movimientoListDataProvider = DataProvider.ofCollection(this.movimientoList);
        movimientoListDataProvider.setSortOrder(Movimiento::getFechaOperacion, SortDirection.DESCENDING);

        movimientoGrid.setDataProvider(movimientoListDataProvider);

    }

    private void configureGrid(){
        loadGrid();
//        movimientoGrid.setSizeFull();
        movimientoGrid.setWidth(Uti.ANCHO_INICIO);
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
        movimientoGrid.addColumn(item -> TarjetasView.tarjetaUltimosDigitos(item.getNumTarjeta())).setHeader("Tarjeta")
                .setSortable(true).setFlexGrow(0).setWidth("110px");
        movimientoGrid.addColumn(Movimiento::getCantidad).setHeader("Cantidad").setSortable(true).setFlexGrow(0).setWidth("110px");
        movimientoGrid.addColumn(Movimiento::getConcepto).setHeader("Concepto").setSortable(true);
        movimientoGrid.addColumn(Movimiento::getFechaOperacion).setHeader("Fecha").setSortable(true);

        movimientoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);

        add(movimientoGrid);
    }

}
