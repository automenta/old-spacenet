package automenta.spacenet.run.graph;

import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger.ForceDirectedParameters;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.number.DoubleVar;

public class ForceDirectedControlWindow extends Window {

    private final ForceDirectedGraphArranger fd;

    public ForceDirectedControlWindow(final ForceDirectedGraphArranger fd) {
        super();
        this.fd = fd;
    }

    @Override
    public void start() {
        super.start();
        ForceDirectedParameters params = fd.getParams();
        Slider repulsionSlider = add(new Slider(params.getRepulsion(), new DoubleVar(-0.5), new DoubleVar(0.5), new DoubleVar(0.1), Slider.SliderType.Horizontal));
        Slider lengthFactorSlider = add(new Slider(params.getLengthFactor(), new DoubleVar(0.5), new DoubleVar(5.0), new DoubleVar(0.1), Slider.SliderType.Horizontal));
        Slider stiffnessSlider = add(new Slider(params.getStiffness(), new DoubleVar(0), new DoubleVar(0.2), new DoubleVar(0.1), Slider.SliderType.Horizontal));
        TextButton clearButton = add(new TextButton("Clear"));
        clearButton.addButtonAction(new ButtonAction() {

            @Override
            public void onButtonPressed(Button b) {
                fd.getGraph().clear();
            }
        });
        TextButton zButton = add(new TextButton("2D/3D"));
        zButton.addButtonAction(new ButtonAction() {

            boolean state = false;
            double zDepth = fd.getBoundsMax().z();

            @Override
            public void onButtonPressed(Button b) {
                state = !state;
                if (state) {
                    fd.getBoundsMax().setZ(0);
                } else {
                    //3D
                    fd.getBoundsMax().setZ(zDepth);
                }
            }
        });
        repulsionSlider.scale(1.0, 0.1).move(0, 0.5);
        lengthFactorSlider.scale(1.0, 0.1).move(0, 0.3);
        stiffnessSlider.scale(1.0, 0.1).move(0, 0.1);
        zButton.span(-0.5, -0.5, -0.25, -0.25);
        clearButton.span(0.5, -0.5, 0.25, -0.25);
        widgets().pullDZ(repulsionSlider, lengthFactorSlider, stiffnessSlider, zButton, clearButton);
    }
}
