## Google Coral on Chronoptics Kea Camera

Requirements 
- Camera connected to the internet, so can download updates
- A USB Host DTB (see instructions on how to change to USB host DTB)

To run the examples
- Google Coral device 

These are the instructions on how to use the Google Coral on the Chronoptics Kea camera. This is the basic yocto layer to get the google coral stick working on the Chronoptics Kea camera. 

Google provides the cross-compiling instructions, https://coral.ai/docs/notes/build-coral/ , unfortunately they use docker to cross-compile. To get a yocto layer working we have done the cross compiling and copied the binary output to the yocto recipe. That way we can get examples demonstrated, however it is not an ideal development environment.  

There are two required components 
- Edge TPU Runtime (libedgetpu)
- TF Lite Runtime 

Two API's 
- libcoral API, C++ library 
- PyCoral API, Python library 

The current method is the Python Wheel's are loaded onto the rootfs, and have to be installed later. This is because of versioning issues with Numpy and Pillow. 
- numpy >= 1.19 
- pillow >= 8.0.4 
- opencv-python >= 4.5.4

Add the yocto layer to the sources folder 

> $ cd ~/var-fslc-yocto/sources \ 
> $ git clone https://github.com/rzw2/meta-chronoptics-coral \ 
> $ cd ~/var-fslc-yocto/build_xwayland \ 
> $ echo 'BBLAYERS += "${BSPDIR}/sources/meta-chronoptics-coral"' >> conf/bblayers.conf 

Add the extra dependencies 
> $ echo 'IMAGE_INSTALL_append = "libedgetpu"' >> conf/local.conf \
> $ echo 'IMAGE_INSTALL_append = "git"' >> conf/local.conf \
> $ echo 'IMAGE_INSTALL_append = "pycoral"' >> conf/local.conf \
> $ echo 'IMAGE_INSTALL_append = "tflite-runtime"' >> conf/local.conf \
> $ echo 'IMAGE_INSTALL_append = "python3-pip"' >> conf/local.conf \
> $ echo 'IMAGE_INSTALL_append = "python3-opencv"' >> conf/local.conf \

Now build the image
> $ bitbake fsl-image-gui 

Flash the SD card 
> sudo umount /dev/sdX* \
> $ zcat tmp/deploy/images/kea-c-rev-b/fsl-image-gui-kea-c-rev-b.wic.gz | sudo dd of=/dev/sdX bs=1M conv=fsync 

Where X is the SD drive, for example /dev/sda* 

Change from the default DTB to the USB Host DTB. 
> $ cd /media/USERNAME/root/boot \ 
> $ sudo rm imx8mq-var-dart-dt8mcustomboard.dtb \
> $ sudo ln -s kea-c-rev-b-host.dtb imx8mq-var-dart-dt8mcustomboard.dtb

Put the SD card into the camera and power the camera to boot. 

Log into the camera, either SSH, or use the USB Debug UART, or the HDMI screen with a mouse and keyboard. 

In the terminal install the python wheel packages
> $ cd /home/root/coral \
> $ python3 -m pip install tflite_runtime-2.5.0.post1-cp38-cp38-linux_aarch64.whl \
> $ python3 -m pip install pycoral-2.0.0-cp38-cp38-linux_aarch64.whl

Now download an example, we use ( https://github.com/google-coral/examples-camera ). 

> $ mkdir google-coral && cd google-coral \
> $ git clone https://github.com/google-coral/examples-camera.git --depth 1 \
> $ cd examples-camera \
> $ sh download_models.sh \
> $ cd opencv 

To run from the terminal you have to select the display to use
> $ DISPLAY=:0 python3 detect.py --camera_idx 1 

## Cross compiling 
The Kea camera uses an IMX8 processor, which is a armv8 instruction set. When you are cross-compiling select aarch64.  

Alternativily you can cross compile and copy across to the SD card. These instructions from google go more in depth.  
https://coral.ai/docs/notes/build-coral/

## TensorFlow Lite Examples 
There are examples of using TensorFlow here
https://www.tensorflow.org/lite/examples
The examples that can run on Raspberry Pi should be able to run on Kea as well, however have to change the OpenCV dependency because the version of OpenCV installed with pip depends on qt which fails to launch the OpenCV image viewer. 

So for https://github.com/tensorflow/examples/tree/master/lite/examples/object_detection/raspberry_pi 
Follow the instructions to clone the repository. 

Remove these two lines from the requirements.txt file 
> numpy>=1.20.0  # To ensure compatibility with OpenCV on Raspberry Pi. \
> opencv-python~=4.5.3.56

Now run the setup.sh script and run the example using 
> DISPLAY=:0 python3 detect.py --enableEdgeTPU --cameraId 1



## Examples

Now the Coral stick woks on the camera. You can combine the chronoptics.tof python library (already installed on the camera) to configure the depth camera, get depth frames and pass it to the Google coral for inference. Or use the C++ API's as well. 

The Google Coral has examples here: https://coral.ai/examples/ , I recommend using the Yocto SDK to cross compile the examples and load onto the camera. 

## Other Examples 
Other examples of using the google coral 
 - https://github.com/Namburger/edgetpu-minimal-example
This is an example of how to cross compile a C++ program and compile the tf library at the same time. To build 

## Recommended Development Enviornment 
The Google Coral toolchain uses docker which is different from the Kea camera of Yocto. So we recommend developing on a host linux machine, and testing before deploying to the camera. To do use
 - Use KeaCamera() on the host Machine, and EmbeddedKeaCamera() on the camera. 
 - KeaCamera() requires a serial number, while EmbeddedKeaCamera() does not. 
 - Enable embedded depth processing on KeaCamera() to generate the same depth information. 
