#!/bin/bash

#installing atom
wget -O atom-amd64.deb http://atom.io/download/deb
sudo apt install gdebi-core
sudo gdebi atom-amd64.deb

#installing git
sudo apt update
sudo apt install git
git config --global user.name "Mariola Rumlova"
git config --global user.email "mariolarumlova@gmail.com"

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
