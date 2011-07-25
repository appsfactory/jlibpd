jlibpd is a port of libpd (http://gitorious.org/pdlib/) to J2ME
primarily so that patches created in Pure Data (http://puredata.info/) can be executed on BlackBerry devices.

Like libpd, jlibpd is released under the BSD License, we just need to
get all the boilerplate in place. 

## Building
*Note: This is only tested under Ubuntu 11.04*
This project requires that you have [Cibyl](https://github.com/SimonKagstrom/cibyl) set up on your system already

Download a pre-compiled uClibc development system:

    cd ~/Downloads
    wget http://www.kernel.org/pub/linux/libs/uclibc/root_fs_mips.ext2.bz2
    bunzip2 root_fs_mips.ext2.bz2

Mount the filesystem where you like, or directly in your jlibpd directory:

    mkdir uclibc_fs
    sudo mount -o loop root_fs_mips.ext2 uclibc_fs
    sudo chown -R [user] uclibc_fs

Link to your uClibc development filesystem:  
*(If you mounted the filesystem in your jlibpd directory, skip this step)*

    ln -s /path/to/uclibc_fs /path/to/jlibpd/uclibc_fs

Finally, run make:  
*Currently, make only produces MIPS for source files under pdlib/pure-data/src*

    make
