SUMMARY = "Tensor FLow Lite Runtime"
LICENSE = "CLOSED" 

SRC_URI = " \
    file://tflite_runtime-2.5.0.post1-cp38-cp38-linux_aarch64.whl \
    "

# Lets test this by copying it first onto the rootfs 

RDEPENDS_${PNG} = "python3-numpy python3-pip libedgetpu"

coraldir = "/home/root/coral"

do_install() {
    install -d ${D}${coraldir}

        mkdir -p ${D}${coraldir}
        cp ${WORKDIR}/tflite_runtime-2.5.0.post1-cp38-cp38-linux_aarch64.whl ${D}${coraldir}/tflite_runtime-2.5.0.post1-cp38-cp38-linux_aarch64.whl
}

FILES_${PN} += "${coraldir}/tflite_runtime-2.5.0.post1-cp38-cp38-linux_aarch64.whl"
