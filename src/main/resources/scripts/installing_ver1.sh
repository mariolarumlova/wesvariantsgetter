#!/bin/bash

#installing Miniconda3
wget https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh
bash Miniconda-3-latest-Linux-x86_64.sh
# delete the installer after successful run
rm Miniconda3-latest-Linux-x86_64.sh
echo 'export PATH="/home/manager/miniconda3/bin:$PATH"' >> ~/.bashrc
echo 'export PATH="/home/manager/miniconda3/bin:$PATH"' >> ~/.zshrc

#cd /home/mariola/miniconda3/pkgs/

#installing BWA-MEM
#wget https://sourceforge.net/projects/bio-bwa/files/latest/download

#downloading samtools
#wget https://github.com/samtools/samtools/releases/download/1.9/samtools-1.9.tar.bz2
#tar xvjf samtools-1.9.tar.bz2
