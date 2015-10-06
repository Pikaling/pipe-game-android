package uk.co.plasmabeamgames.pipegame.core.viewmodel;

public class ConveyorTileViewModel {

    private int angle;
    private int imageId;

    public ConveyorTileViewModel(int angle, int imageId) {
        this.angle = angle;
        this.imageId = imageId;
    }

    public int getAngle() {
        return angle;
    }

    public int getImageId() {
        return imageId;
    }
}
