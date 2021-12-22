SUMMARY = "PyCoral API"
LICENSE = "CLOSED" 

SRC_URI = " \
    file://pycoral-2.0.0-cp38-cp38-linux_aarch64.whl \
    "

# Lets test this by copying it first onto the rootfs 

RDEPENDS_${PNG} = "python3-numpy python3-pillow python3-pip libedgetpu tflite-runtime python3-opencv"

coraldir = "/home/root/coral"

do_install() {
    install -d ${D}${coraldir}

        mkdir -p ${D}${coraldir}
        cp ${WORKDIR}/pycoral-2.0.0-cp38-cp38-linux_aarch64.whl ${D}${coraldir}/pycoral-2.0.0-cp38-cp38-linux_aarch64.whl
}

FILES_${PN} += "${coraldir}/pycoral-2.0.0-cp38-cp38-linux_aarch64.whl"
