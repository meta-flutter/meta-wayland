SUMMARY = "A wlroots-compatible Wayland color picker that does not suck."
HOMEPAGE = "https://hyprland.org/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=936078e4e67b0e1e1bd1e862d4ffbc25"

SRC_URI = "git://github.com/hyprwm/hyprpicker.git;protocol=https;branch=main"

PV = "0.1.1"
S = "${WORKDIR}/git"
SRCREV = "deaca6a4d8e307d90b1f5b1033a8ea6c83b9b260"

DEPENDS = " \
	cairo \
	fribidi \
	jpeg \
	libxdmcp \
	pango \
	pcre \
	util-linux \
	wayland \
	wayland-native \
	wayland-protocols \
	wlroots \
"

RDEPENDS:${PN} = "wl-clipboard"

REQUIRED_DISTRO_FEATURES = "wayland"

EXTRA_OEMAKE = "\
    WAYLAND_SCANNER=${STAGING_BINDIR_NATIVE}/wayland-scanner \
    WAYLAND_PROTOCOLS=${STAGING_DATADIR}/wayland-protocols \
"

do_configure:prepend() {
    sed -i -e '$ainstall(TARGETS hyprpicker)' ${S}/CMakeLists.txt
}

do_compile:prepend() {
    cd ${S} && oe_runmake protocols
}

do_install() {
    install -d ${D}${bindir}
    install -m0755 ${B}/hyprpicker ${D}${bindir}
}

inherit cmake pkgconfig features_check
