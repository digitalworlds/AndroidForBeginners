package gl.renderers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public abstract class GyroscopicRenderer extends GLRenderer implements SensorEventListener {

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private String cameraId;
    protected CameraDevice cameraDevice;
    private TextureView textureView;
    private Size imageDimension;
    protected CaptureRequest.Builder captureRequestBuilder;
    protected CameraCaptureSession cameraCaptureSessions;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private float rotation_center_X=0;
    private float rotation_center_Y=0;
    private float rotation_center_Z=0;

    private float FOV=40;

    public void setRotationCenter(float x, float y, float z){
        rotation_center_X=x;
        rotation_center_Y=y;
        rotation_center_Z=z;
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //open your camera here
            openCamera();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            //This is called when the camera is open
            cameraDevice = camera;
            createCameraPreview();
        }
        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }
        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            //assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(getContext(), "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        /*if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }*/
    }

    protected void updatePreview() {
        if(null == cameraDevice) {

        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private SensorManager mSensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;


    public GyroscopicRenderer(Activity activity) {
        super(activity);

        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (gyroscope != null) {
            mSensorManager.registerListener(this, gyroscope,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }

        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void setupProjection(int width, int height){
        float ratio = (float) width / height;
        Matrix.perspectiveM(mProjectionMatrix,0,this.FOV,ratio,0.1f, 1024);
    }

    public void setFOV(float fov){
        this.FOV=fov;
        this.onSurfaceChanged(null,this.width,this.height);
    }
    public float getFOV(){return this.FOV;}

    public void setupView(){
        view.identity().translate(rotation_center_X,rotation_center_Y,rotation_center_Z).multiply(m_rotationMatrix).rotateX(90).translate(-rotation_center_X,-rotation_center_Y,-rotation_center_Z);
    }

    float[] m_lastMagFields = new float[3];;
    float[] m_lastAccels = new float[3];;
    private float[] m_rotationMatrix = new float[16];
    private float[] m_orientation = new float[4];

    public float Pitch = 0.f;
    public float Heading = 0.f;
    public float Roll = 0.f;
    public float Declination = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, m_lastAccels, 0, 3);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, m_lastMagFields, 0, 3);
                break;
            default:
                return;
        }

        computeOrientation();
    }

    private void computeOrientation() {
        if (SensorManager.getRotationMatrix(m_rotationMatrix, null, m_lastAccels, m_lastMagFields)) {
            SensorManager.getOrientation(m_rotationMatrix, m_orientation);

            float yaw = (float) (Math.toDegrees(m_orientation[0]) + Declination);
            float pitch = (float) Math.toDegrees(m_orientation[1]);
            float roll = (float) Math.toDegrees(m_orientation[2]);

            Heading = Heading*0.9f+yaw*0.1f;
            Pitch = Pitch*0.9f+pitch*0.1f;
            Roll = Roll*0.9f+roll*0.1f;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void destroy(){
        if(gyroscope!=null) mSensorManager.unregisterListener(this,gyroscope);
        if(accelerometer!=null) mSensorManager.unregisterListener(this,accelerometer);
        super.destroy();
    }

    public void resumeCamera(){
        if(textureView==null)return;
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    public void pauseCamera(){
        if(textureView==null)return;
        closeCamera();
        stopBackgroundThread();
    }

    public void showCamera(View view)
    {
        textureView=(TextureView)view;
        textureView.setSurfaceTextureListener(textureListener);

    }
}
