SUMMARY = "Web-based MySQL administration interface"
HOMEPAGE = "http://www.phpmyadmin.net"
# Main code is GPLv2, libraries/tcpdf is under LGPLv3, js/jquery is under MIT
LICENSE = "GPLv2 & LGPLv3 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=eb723b61539feef013de476e68b5c50a \
                    file://libraries/tcpdf/LICENSE.TXT;md5=5c87b66a5358ebcc495b03e0afcd342c"

SRC_URI = "${SOURCEFORGE_MIRROR}/phpmyadmin/phpMyAdmin/${PV}/phpMyAdmin-${PV}-all-languages.tar.xz \
           file://0001-bug-4504-security-Self-XSS-in-query-charts.patch \
           file://0001-bug-4505-security-XSS-in-view-operations-page.patch \
           file://0001-Bug-4544-additional-fix-for-4.2.x.patch \
           file://apache.conf \
           file://0001-bug-4530-security-DOM-based-XSS-that-results-to-a-CS.patch \
           file://0001-bug-4562-security-XSS-in-debug-SQL-output.patch \
           file://0001-bug-4563-security-XSS-in-monitor-query-analyzer.patch \
           file://0001-bug-4594-security-Path-traversal-in-file-inclusion-o.patch \
           file://0001-bug-4579-security-XSS-vulnerability-in-zoom-search-p.patch \
           file://0001-bug-4578-security-XSS-vulnerability-in-table-print-v.patch \
           file://0001-bug-4597-security-XSS-through-pma_fontsize-cookie.patch \
           file://0001-bug-4598-security-XSS-in-multi-submit.patch"

SRC_URI[md5sum] = "0dcd755450dac819f33502590c88ad29"
SRC_URI[sha256sum] = "5d101dd88a99a869bc0c684a7f687cf290abc4bf306daac73337cbde2d7743e4"

S = "${WORKDIR}/phpMyAdmin-${PV}-all-languages"

inherit allarch

do_install() {
    install -d ${D}${datadir}/${BPN}
    cp -a * ${D}${datadir}/${BPN}

    install -d ${D}${sysconfdir}/apache2/conf.d
    install -m 0644 ${WORKDIR}/apache.conf ${D}${sysconfdir}/apache2/conf.d/phpmyadmin.conf

    # Remove a few scripts that explicitly require bash (!)
    rm -f ${D}${datadir}/phpmyadmin/libraries/transformations/*.sh
}

FILES_${PN} = "${datadir}/${BPN} \
               ${sysconfdir}/apache2/conf.d"

RDEPENDS_${PN} += "bash"
