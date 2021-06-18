//package io.oferto.application.views.categoria;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.GridVariant;
//import com.vaadin.flow.component.html.*;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.data.provider.DataProvider;
//import com.vaadin.flow.data.provider.ListDataProvider;
//import com.vaadin.flow.data.provider.SortDirection;
//import com.vaadin.flow.router.Route;
//
//import Categoria;
//import CategoriaService;
////import io.oferto.application.components.AuthenticatedButton;
////import io.oferto.application.security.SecurityConfiguration;
//import MainView;
//
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.component.dependency.CssImport;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//@Route(value = "categoria", layout = MainView.class)
//@PageTitle("Categoria")
//@CssImport("./views/about/about-view.css")
//public class CategoriaView extends VerticalLayout {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private Grid<Categoria> gridCategoria = new Grid<>(Categoria.class);
//    private ListDataProvider<Categoria> productProvider;
//    private List<Categoria> categorias;
//
//    private final CategoriaService categoriaService;
//    public CategoriaView(CategoriaService categoriaService) {
//        this.categoriaService = categoriaService;
//
//        addClassName("about-view");
//        setSizeFull();
//        setPadding(true);
//
//
//        // Botón para crear una nueva Categoria
//        Button btnCrearCategoria = new Button("Crear Categoria");
//        btnCrearCategoria.addClickListener(event ->{
//            CategoriaForm formularioCategoria = new CategoriaForm(null, categoriaService);
//            formularioCategoria.open();
//            formularioCategoria.addOpenedChangeListener(event1 -> {
//               loadData();
//               loadGrid();
//            });
//
//        });
//
//        this.add(btnCrearCategoria, new Hr());
//
////****************************************************************************
//
//
//        // load data from service
//        loadData();
//
//        // fill grid with data
//        configureGrid();
//
//        // create view layput
//        add(gridCategoria);
//
//
//
//    }
//
//
//    private void loadData() {
//        try {
//            this.categorias = categoriaService.encuentraCategorias();
//        }
//        catch(Exception ex) {
//            ex.printStackTrace();
//
//            logger.debug(ex.getLocalizedMessage());
//        }
//    }
//
//    private void loadGrid() {
//        productProvider =  DataProvider.ofCollection(this.categorias);
//        productProvider.setSortOrder(Categoria::getTipoCategoria, SortDirection.ASCENDING);
//
//        gridCategoria.setDataProvider(productProvider);
//    }
//
//
//    private void configureGrid() {
//        loadGrid();
//
//        gridCategoria.setSizeFull();
//        gridCategoria.setColumns("tipoCategoria");
//
//        gridCategoria.getColumnByKey("tipoCategoria").setFlexGrow(1).setWidth("200px").setHeader("Nombre");
//
//
//        if (SecurityConfiguration.isAdmin()) {
//            gridCategoria.addComponentColumn(item -> updateProductButton(gridCategoria, item)).setFlexGrow(0).setWidth("120px").setHeader("");
//            gridCategoria.addComponentColumn(item -> RemoveButton(gridCategoria, item)).setFlexGrow(0).setWidth("120px").setHeader("");
//        }
//
//        gridCategoria.addThemeVariants(GridVariant.LUMO_NO_BORDER,
//                GridVariant.LUMO_NO_ROW_BORDERS,
//                GridVariant.LUMO_ROW_STRIPES);
//    }
//
//    private Component RemoveButton(Grid<Categoria> gridProduct, Categoria categoria) {
//        AuthenticatedButton button = new AuthenticatedButton("Borrar");
//            button.addClickListener(event -> {
//                try{
//                    categoriaService.borrarCategoria(categoria);
//                }catch(Exception e){
//                    logger.error("Error durante el borrado de la categoria: {}", categoria.getTipoCategoria());
//                    Notification.show("Se ha producido un error.", 2000, Notification.Position.MIDDLE);
//                }
//                loadData();
//                loadGrid();
//            });
//        return button;
//
//    }
//
//
//    private Button updateProductButton(Grid<Categoria> grid, Categoria categoria) {
//        AuthenticatedButton button = new AuthenticatedButton("Actualizar");
//        button.addClickListener(event -> {
//            CategoriaForm formularioCategoria = new CategoriaForm(categoria, categoriaService);
//            formularioCategoria.open();
//            formularioCategoria.addOpenedChangeListener(event1 -> {
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
//}
