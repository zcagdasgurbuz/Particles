package com.zeynelcgurbuz.particles.ui;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.redux.Subscriber;
import com.zeynelcgurbuz.particles.redux.Subscription;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.SaveLoadService;
import com.zeynelcgurbuz.particles.store.actions.RestartAction;
import com.zeynelcgurbuz.particles.store.actions.SetStateAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * The type Menu content controller.
 */
public class MenuContentController implements Subscriber<ParticlesState> {

    /**
     * The Config name field.
     */
    public TextField configNameField;
    /**
     * The Config names list view.
     */
    public ListView<ParticlesState> configNamesListView;
    /**
     * The Save button.
     */
    public Button saveButton;
    /**
     * The Load button.
     */
    public Button loadButton;
    /**
     * The Remove button.
     */
    public Button removeButton;
    /**
     * The Attraction standard.
     */
//
    public CheckBox attractionStandard;
    /**
     * The Negate self attr.
     */
    public CheckBox negateSelfAttr;
    /**
     * The Min r standard.
     */
    public CheckBox minRStandard;
    /**
     * The Max r standard.
     */
    public CheckBox maxRStandard;
    /**
     * The Gravitational attr.
     */
    public CheckBox gravitationalAttr;
    /**
     * The Molecular attr.
     */
    public CheckBox molecularAttr;
    /**
     * The Is walls active.
     */
    public CheckBox isWallsActive;
    /**
     * The In range style.
     */
    public ComboBox<String> inRangeStyle;
    /**
     * The Below range style.
     */
    public ComboBox<String> belowRangeStyle;
    /**
     * The Out range style.
     */
    public ComboBox<String> outRangeStyle;
    /**
     * The Particles count spinner.
     */
    public Spinner<Integer> particlesCountSpinner;
    /**
     * The Particle types spinner.
     */
    public Spinner<Integer> particleTypesSpinner;
    /**
     * The Flat radius spinner.
     */
    public Spinner<Double> flatRadiusSpinner;
    /**
     * The G spinner.
     */
    public Spinner<Double> gSpinner;
    /**
     * The Friction spinner.
     */
    public Spinner<Double> frictionSpinner;
    /**
     * The Attr mean.
     */
    public Spinner<Double> attrMean;
    /**
     * The Attr std.
     */
    public Spinner<Double> attrStd;
    /**
     * The Attr min.
     */
    public Spinner<Double> attrMin;
    /**
     * The Attr max.
     */
    public Spinner<Double> attrMax;
    /**
     * The Min r mean.
     */
    public Spinner<Double> minRMean;
    /**
     * The Min r std.
     */
    public Spinner<Double> minRStd;
    /**
     * The Min r min.
     */
    public Spinner<Double> minRMin;
    /**
     * The Min r max.
     */
    public Spinner<Double> minRMax;
    /**
     * The Max r mean.
     */
    public Spinner<Double> maxRMean;
    /**
     * The Max r std.
     */
    public Spinner<Double> maxRStd;
    /**
     * The Max r min.
     */
    public Spinner<Double> maxRMin;
    /**
     * The Max r max.
     */
    public Spinner<Double> maxRMax;
    /**
     * The Restart button.
     */
    public Button restartButton;
    /**
     * The Restart required alert.
     */
    public Label restartRequiredAlert;

    /**
     * The Startup last state.
     */
    public CheckBox startupLastState;

    /**
     * The Store subscription.
     */
    private Subscription storeSubscription = null;

    /**
     * The Store.
     */
    private Store<ParticlesState> store;
    /**
     * The State.
     */
    private ParticlesState state;
    /**
     * The Converter.
     */
    private StringConverter<Double> converter = null;
    /**
     * The Need recalculation.
     */
    @FXML
    public BooleanProperty needRecalculation;
    /**
     * The Funcs.
     */
    ObservableList<String> funcs = null;
    /**
     * The Save load service.
     */
    private SaveLoadService saveLoadService;

    /**
     * Instantiates a new Menu content controller.
     */
    MenuContentController() {
    }

    /**
     * Instantiates a new Menu content controller.
     *
     * @param store           the store
     * @param saveLoadService the save load service
     */
    public MenuContentController(Store<ParticlesState> store, SaveLoadService saveLoadService) {
        //super();
        this.store = store;
        this.saveLoadService = saveLoadService;
        needRecalculation = new SimpleBooleanProperty(false);
        //decimal format of spinners, taken from api
        converter = new StringConverter<>() {
            private final DecimalFormat df = new DecimalFormat("#.#####");
            @Override
            public String toString(Double value) {
                // If the specified value is null, return a zero-length String
                if (value == null) {
                    return "";
                }
                return df.format(value);
            }
            @Override
            public Double fromString(String value) {
                try {
                    // If the specified value is null or zero-length, return null
                    if (value == null) {
                        return null;
                    }
                    value = value.trim();
                    if (value.length() < 1) {
                        return null;
                    }
                    // Perform the requested parsing
                    return df.parse(value).doubleValue();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        ArrayList<String> funcsArray = new ArrayList<>();
        funcsArray.add("<<not defined>>");
        funcsArray.add("attr1 + attr2");
        funcsArray.add("attr1 - attr2");
        funcsArray.add("attr2 - attr1");
        funcsArray.add("(attr1 * attr2) / r^2");
        funcsArray.add("-(attr1 * attr2) / r^2");
        funcsArray.add("(attr1 + attr2) / r^2");
        funcsArray.add("(attr1 - attr2) / r^2");
        funcsArray.add("(attr2 - attr1) / r^2");
        funcsArray.add("1 / r^2");
        funcsArray.add("-1 / r^2");
        funcsArray.add("attr1");
        funcsArray.add("attr2");
        funcsArray.add("-attr1");
        funcsArray.add("-attr2");
        funcsArray.add("attr1 / r^2");
        funcsArray.add("attr2 / r^2");
        funcsArray.add("-attr1 / r^2");
        funcsArray.add("-attr2 / r^2");
        funcsArray.add("attr1 * (1 - (pt 2 effective) r)");
        funcsArray.add("attr2 * (1 - (pt 1 effective) r)");
        funcsArray.add("minR1 * (1/minR1 - 1/r)");
        funcsArray.add("minR2 * (1/minR2 - 1/r)");
        funcs = FXCollections.observableArrayList(funcsArray);
    }

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        storeSubscription = store.subscribe(this);
        this.state = store.getState();
        gSpinner.getValueFactory().setConverter(converter);
        frictionSpinner.getValueFactory().setConverter(converter);
        flatRadiusSpinner.getValueFactory().setConverter(converter);
        attrMean.getValueFactory().setConverter(converter);
        attrStd.getValueFactory().setConverter(converter);
        attrMin.getValueFactory().setConverter(converter);
        attrMax.getValueFactory().setConverter(converter);
        minRMean.getValueFactory().setConverter(converter);
        minRStd.getValueFactory().setConverter(converter);
        minRMin.getValueFactory().setConverter(converter);
        minRMax.getValueFactory().setConverter(converter);
        maxRMean.getValueFactory().setConverter(converter);
        maxRStd.getValueFactory().setConverter(converter);
        maxRMin.getValueFactory().setConverter(converter);
        maxRMax.getValueFactory().setConverter(converter);
        restartRequiredAlert.visibleProperty().bind(needRecalculation);
        restartRequiredAlert.setTextFill(Color.RED);
        inRangeStyle.setItems(funcs);
        belowRangeStyle.setItems(funcs);
        outRangeStyle.setItems(funcs);
        setValues();
        setListeners();
        configNameField.textProperty().addListener((observable, oldValue, newValue) ->
                saveButton.setDisable(newValue.length() <= 0));
        configNamesListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                loadButton.setDisable(false);
                removeButton.setDisable(false);
            } else {
                loadButton.setDisable(true);
                removeButton.setDisable(true);
            }
        });
    }

    /**
     * Sets values.
     */
    private void setValues() {
        attractionStandard.setSelected(state.isAttractionStandard());
        negateSelfAttr.setSelected(state.isNegateSelfAttraction());
        minRStandard.setSelected(state.isMinRStandard());
        maxRStandard.setSelected(state.isMaxRStandard());
        gravitationalAttr.setSelected(state.isGravAttract());
        molecularAttr.setSelected(state.isMolAttract());
        isWallsActive.setSelected(state.isWallsActive());
        //combooooox
        inRangeStyle.getSelectionModel().select(state.getInRangeStyle());
        belowRangeStyle.getSelectionModel().select(state.getBelowRangeStyle());
        outRangeStyle.getSelectionModel().select(state.getOutRangeStyle());
        //
        particlesCountSpinner.getValueFactory().setValue(state.getParticleCount());
        particleTypesSpinner.getValueFactory().setValue(state.getColorCount());
        flatRadiusSpinner.getValueFactory().setValue(state.getFlatRadius());
        gSpinner.getValueFactory().setValue(state.getG());
        frictionSpinner.getValueFactory().setValue(state.getFriction());
        attrMean.getValueFactory().setValue(state.getAttractionMean());
        attrStd.getValueFactory().setValue(state.getAttractionStd());
        attrMin.getValueFactory().setValue(state.getAttractionMin());
        attrMax.getValueFactory().setValue(state.getAttractionMax());
        minRMean.getValueFactory().setValue(state.getMinRMean());
        minRStd.getValueFactory().setValue(state.getMinRStd());
        minRMin.getValueFactory().setValue(state.getMinRLower());
        minRMax.getValueFactory().setValue(state.getMinRUpper());
        maxRMean.getValueFactory().setValue(state.getMaxRMean());
        maxRStd.getValueFactory().setValue(state.getMaxRStd());
        maxRMin.getValueFactory().setValue(state.getMaxRLower());
        maxRMax.getValueFactory().setValue(state.getMaxRUpper());
        configNamesListView.setItems(saveLoadService.getStates().sorted());
        startupLastState.setSelected(saveLoadService.isSaveOnClose());
    }

    /**
     * Sets listeners.
     */
    private void setListeners() {

        attractionStandard.selectedProperty().addListener((observable, oldValue, newValue) -> {
            state.setAttractionStandard(newValue);
            needRecalculation.set(true);
        });
        negateSelfAttr.selectedProperty().addListener((observable, oldValue, newValue) -> {
            state.setNegateSelfAttraction(newValue);
            needRecalculation.set(true);
        });
        minRStandard.selectedProperty().addListener((observable, oldValue, newValue) -> {
            state.setMinRStandard(newValue);
            needRecalculation.set(true);
        });
        maxRStandard.selectedProperty().addListener((observable, oldValue, newValue) -> {
            state.setMaxRStandard(newValue);
            needRecalculation.set(true);
        });
        particlesCountSpinner.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setParticleCount(newValue);
            needRecalculation.set(true);
        });
        particleTypesSpinner.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setColorCount(newValue);
            needRecalculation.set(true);
        });
        flatRadiusSpinner.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setFlatRadius(newValue);
            needRecalculation.set(true);
        });
        attrMean.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setAttractionMean(newValue);
            needRecalculation.set(true);
        });
        attrStd.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setAttractionStd(newValue);
            needRecalculation.set(true);
        });
        attrMin.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setAttractionMin(newValue);
            needRecalculation.set(true);
        });
        attrMax.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setAttractionMax(newValue);
            needRecalculation.set(true);
        });
        minRMean.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMinRMean(newValue);
            needRecalculation.set(true);
        });
        minRStd.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMinRStd(newValue);
            needRecalculation.set(true);
        });
        minRMin.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMinRLower(newValue);
            needRecalculation.set(true);
        });
        minRMax.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMinRUpper(newValue);
            needRecalculation.set(true);
        });
        maxRMean.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMaxRMean(newValue);
            needRecalculation.set(true);
        });
        maxRStd.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMaxRStd(newValue);
            needRecalculation.set(true);
        });
        maxRMin.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMaxRLower(newValue);
            needRecalculation.set(true);
        });
        maxRMax.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setMaxRUpper(newValue);
            needRecalculation.set(true);
        });
        inRangeStyle.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            state.setInRangeStyle(newValue.intValue());
            requestSetState();
        });
        belowRangeStyle.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            state.setBelowRangeStyle(newValue.intValue());
            requestSetState();
        });
        outRangeStyle.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            state.setOutRangeStyle(newValue.intValue());
            requestSetState();
        });
        gravitationalAttr.selectedProperty().addListener((observable, oldValue, newValue) -> {
            state.setGravAttract(newValue);
            requestSetState();
        });
        molecularAttr.selectedProperty().addListener((observable, oldValue, newValue) -> {
            state.setMolAttract(newValue);
            requestSetState();
        });
        isWallsActive.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                state.setMouseDragPosition(new Vector());
            }
            state.setWallsActive(newValue);
            requestSetState();
        });
        gSpinner.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setG(newValue);
            requestSetState();
        });
        frictionSpinner.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
            state.setFriction(newValue);
            requestSetState();
        });
        startupLastState.selectedProperty().addListener((observable, oldValue, newValue) ->
                saveLoadService.setSaveOnClose(newValue));
    }

    /**
     * Save button action.
     *
     * @param actionEvent the action event
     */
//save load state...
    public void saveButtonAction(ActionEvent actionEvent) {
        String name = configNameField.textProperty().get();
        if (saveLoadService.requestSave(name))
            configNameField.textProperty().set("");
    }

    /**
     * Load button action.
     *
     * @param actionEvent the action event
     */
    public void loadButtonAction(ActionEvent actionEvent) {
        saveLoadService.requestLoad(configNamesListView.getSelectionModel().getSelectedItem());
        needRecalculation.set(false);
    }

    /**
     * Remove button action.
     *
     * @param actionEvent the action event
     */
    public void removeButtonAction(ActionEvent actionEvent) {
        saveLoadService.requestRemove(configNamesListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Request set state.
     */
    private void requestSetState() {
        store.dispatch(new SetStateAction(state));
    }

    @Override
    public void onChange(ParticlesState state) {
        this.state = state.shallowCopy();
        setValues();
    }

    /**
     * Restart button.
     *
     * @param actionEvent the action event
     */
    public void restartButton(ActionEvent actionEvent) {
        requestSetState();
        store.dispatch(new RestartAction());
        needRecalculation.set(false);
    }
}
