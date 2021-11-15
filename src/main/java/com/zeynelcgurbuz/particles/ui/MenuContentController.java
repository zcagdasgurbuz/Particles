package com.zeynelcgurbuz.particles.ui;

import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.redux.Subscriber;
import com.zeynelcgurbuz.particles.redux.Subscription;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.actions.RestartAction;
import com.zeynelcgurbuz.particles.store.actions.SaveRequestAction;
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
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class MenuContentController implements Subscriber<ParticlesState> {

    public TextField configNameField;
    public ListView<ParticlesState> configNamesListView;
    public Button saveButton;
    public Button loadButton;
    public Button removeButton;
    //
    public CheckBox attractionStandard;
    public CheckBox negateSelfAttr;
    public CheckBox minRStandard;
    public CheckBox maxRStandard;
    public CheckBox gravitationalAttr;
    public CheckBox molecularAttr;
    public CheckBox isWallsActive;
    //
    public ComboBox<String> inRangeStyle;
    public ComboBox<String> belowRangeStyle;
    public ComboBox<String> outRangeStyle;
    //
    public Spinner<Integer> particlesCountSpinner;
    public Spinner<Integer> particleTypesSpinner;
    public Spinner<Double> flatRadiusSpinner;
    public Spinner<Double> gSpinner;
    public Spinner<Double> frictionSpinner;
    public Spinner<Double> attrMean;
    public Spinner<Double> attrStd;
    public Spinner<Double> attrMin;
    public Spinner<Double> attrMax;
    public Spinner<Double> minRMean;
    public Spinner<Double> minRStd;
    public Spinner<Double> minRMin;
    public Spinner<Double> minRMax;
    public Spinner<Double> maxRMean;
    public Spinner<Double> maxRStd;
    public Spinner<Double> maxRMin;
    public Spinner<Double> maxRMax;
    public Button restartButton;
    public Label restartRequiredAlert;
    public BooleanProperty saveLoadExpanded;


    private Subscription storeSubscription = null;

    private Store<ParticlesState> store;
    private ParticlesState state;
    private StringConverter<Double> converter = null;
    @FXML public BooleanProperty needRecalculation;
    ObservableList<String> funcs = null;

    MenuContentController(){}

    public MenuContentController(Store<ParticlesState> store){
        //super();
        this.store = store;
        needRecalculation = new SimpleBooleanProperty(false);
        saveLoadExpanded = new SimpleBooleanProperty(false);
        converter = new StringConverter<Double>() {
            private final DecimalFormat df = new DecimalFormat("#.#####");
            @Override public String toString(Double value) {
                // If the specified value is null, return a zero-length String
                if (value == null) {
                    return "";
                }

                return df.format(value);
            }
            @Override public Double fromString(String value) {
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
        funcs = FXCollections.observableArrayList(funcsArray);
    }

    @FXML
    public void initialize(){
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
        configNameField.textProperty().addListener((observable, oldValue, newValue) -> saveButton.setDisable(newValue.length() <= 0));
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

    private void setValues(){
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
        configNamesListView.setItems(state.getManager().getStates());
    }

    private void setListeners(){

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
    }

    //save load state...
    public void saveButtonAction(ActionEvent actionEvent) {
        String name = configNameField.textProperty().get();
        if(state.getManager().requestSave(name))
        configNameField.textProperty().set("");
    }

    public void loadButtonAction(ActionEvent actionEvent) {
        state.getManager().requestLoad(configNamesListView.getSelectionModel().getSelectedItem());
        needRecalculation.set(false);
    }

    public void removeButtonAction(ActionEvent actionEvent) {
        state.getManager().requestRemove(configNamesListView.getSelectionModel().getSelectedItem());
    }

    private void requestSetState(){
        store.dispatch(new SetStateAction(state));
    }

    @Override
    public void onChange(ParticlesState state) {
        this.state = state.shallowCopy();
        setValues();
    }

    public void restartButton(ActionEvent actionEvent) {
        requestSetState();
        store.dispatch(new RestartAction());
        needRecalculation.set(false);
    }
}
