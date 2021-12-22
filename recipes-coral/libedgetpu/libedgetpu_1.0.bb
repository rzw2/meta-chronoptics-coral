SUMMARY = "Edge TPU library"
LICENSE = "CLOSED"

SRC_URI = " \
    file://libedgetpu.so.1 \
    file://libedgetpu.so.1.0 \
    "

RDEPENDS_${PN} = "libusb1 libcxx"

do_install() {
    install -d ${D}${libdir} 
    cp ${WORKDIR}/libedgetpu.so.1 ${D}${libdir}/libedgetpu.so.1
    cp ${WORKDIR}/libedgetpu.so.1.0 ${D}${libdir}/libedgetpu.so.1.0
}

# We copy across precompiled libraries. 
FILES_${PN} += "${libdir}/libedgetpu.so.1 \
                ${libdir}/libedgetpu.so.1.0 "

INSANE_SKIP_${PN} += "already-stripped"

# LIC_FILES_CHKSUM = "file://libedgetpu/LICENSE.txt;md5=3b9bc721e87e6fe1ca09f3c923502627"
# require recipes-support/common/edgetpu-common.inc

# S = "${WORKDIR}/git"

# RDEPENDS_${PN} = "libusb1 libcxx"

# EDGETPU_THROTTLED = "1"

# python __anonymous() {
#     tunes = d.getVar("TUNE_FEATURES", True)
#     if not tunes:
#         return

#     if "aarch64" in tunes:
#         d.setVar("LIBEDGETPU_SUFFIX", "arm64")
#     elif "x86_64" in tunes:
#         d.setVar("LIBEDGETPU_SUFFIX", "x86_64")
#     elif "callconvention-hard" in tunes:
#         d.setVar("LIBEDGETPU_SUFFIX", "arm32")
#     else:
#         pkgn = d.getVar("PN", True)
#         pkgv = d.getVar("PV", True)
#         raise bb.parse.SkipRecipe("%s-%s: unsupported ARCH: %s" % (pkgn, pkgv, tunes))


#     throttled = d.getVar("EDGETPU_THROTTLED", True)
#     if throttled:
#         d.setVar("LIBEDGETPU_SUFFIX", d.getVar("LIBEDGETPU_SUFFIX", True) + "_throttled")
# }

# do_install() {
#     install -d ${D}${sysconfdir}/udev/rules.d
#     install -m 0644 ${S}/99-edgetpu-accelerator.rules \
#                     ${D}${sysconfdir}/udev/rules.d/99-edgetpu-accelerator.rules

#     install -d ${D}/${libdir}
#     install -m 755 ${S}/libedgetpu/libedgetpu_${LIBEDGETPU_SUFFIX}.so \
#                    ${D}/${libdir}/libedgetpu.so.1.0
#     ln -sf ${libdir}/libedgetpu.so.1.0 ${D}/${libdir}/libedgetpu.so.1
#     ln -sf ${libdir}/libedgetpu.so.1.0 ${D}/${libdir}/libedgetpu.so

#     install -d ${D}/${includedir}
#     install -m 755 ${S}/libedgetpu/edgetpu.h ${D}/${includedir}
# }

# TOOLCHAIN = "clang"

# INSANE_SKIP_${PN} += "already-stripped"
