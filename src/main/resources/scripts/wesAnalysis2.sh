#!/bin/bash

#Enter dir where you want to analyse
#mkdir /home/lukasz/wesData/
#cd /home/lukasz/wesData/
echo ". /home/lukasz/miniconda3/etc/profile.d/conda.sh" >> ~/.bashrc
conda update conda
conda create -n ngs python=3
# activate the environment
conda activate ngs

# Install some conda channels
# A channel is where conda looks for packages
conda config --add channels defaults
conda config --add channels conda-forge
conda config --add channels bioconda

# Install these tools into the conda environment
# if not already installed
conda install samtools
conda install bamtools
conda install freebayes
conda install bedtools
conda install vcflib
conda install rtg-tools
conda install bcftools
conda install ea-utils
conda install fastqc
conda install bowtie2
conda install bwa
conda install picard
conda install gatk

#installing Snakemake
conda install -c bioconda -c conda-forge snakemake

mkdir /home/lukasz/Documents/MRumlova/wesData/genomes/
cd /home/lukasz/Documents/MRumlova/wesData/genomes/

#downloading reference genome hg38
wget http://hgdownload.soe.ucsc.edu/goldenPath/hg38/bigZips/hg38.fa.gz
gunzip hg38.fa.gz

#downloading reference genome hg19
wget hgdownload.cse.ucsc.edu/goldenPath/hg19/bigZips/chromFa.tar.gz
tar -zxvf chromFa.tar.gz
cat chr*.fa > hg19.fa

#indexing reference genome in BWA
bwa index /home/lukasz/Documents/MRumlova/wesData/genomes/hg38.fa

conda activate ngs
conda install cutadapt
conda instal bbmap

mkdir /home/lukasz/Documents/MRumlova/wesData/programs/
cd /home/lukasz/Documents/MRumlova/wesData/programs/

wget https://sourceforge.net/projects/snvsniffer/files/latest/download/SNVSniffer-v2.0.4_bin_x86_64.tar.gz
gunzip SNVSniffer-v2.0.4_bin_x86_64.tar.gz

#install tool to trimming
wget http://14.139.61.3:8080/ngsqctoolkit/NGSQCToolkit_v2.3.3.zip
sudo apt-get install unzip
unzip NGSQCToolkit_v2.3.3.zip

#sudo su
#apt-get update && apt-get install ssmtp
#atom /etc/ssmtp/ssmtp.conf
#Add the following commented text to an opened .conf file
#root=mariolarumlova@gmail.com
#AuthUser=mariolarumlova
#AuthPass=mypassword
#FromLineOverride=YES
#mailhub=smtp.gmail.com:587
#UseSTARTTLS=YES
#apt install mailutils
#service sendmail stop
#chkconfig sendmail off
#mkdir /root/.bakup
#mv /usr/sbin/sendmail /root/.bakup
#ln -s /usr/local/ssmtp/sbin/ssmtp /usr/sbin/sendmail

#install fastqc again
conda config --get channels
conda config --remove channels 'conda-forge'
conda install -n env_name -c bioconda fastqc==0.11.5

sudo apt install sharutils

#wget https://sourceforge.net/projects/solexaqa/files/latest/download/SolexaQA++_v3.1.7.1.zip
#mkdir /home/lukasz/Documents/MRumlova/wesData/programs/SolexaQA/
#cd /home/lukasz/Documents/MRumlova/wesData/programs/SolexaQA/
#unzip /home/lukasz/Documents/MRumlova/wesData/programs/SolexaQA++_v3.1.7.1.zip
#chmod a+x Linux_x64/SolexaQA++
#cd Linux_x64/
#usage: ./SolexaQA++

#git clone https://github.com/optimuscoprime/autoadapt.git
#cd autoadapt/
#make install






#General conda commands
#to search for packages
#conda search [package]

# To update all packages
#conda update --all --yes

# List all packages installed
#conda list [-n env]

# conda list environments
#conda env list

# create new env
#conda create -n [name] package [package] ...

# activate env
#conda activate [name]

# deavtivate env
#conda deactivate

#Install required software into isolated Conda environment
#conda activate
#conda env create --name snakemake-tutorial --file environment.yaml

#Activate snakemake-tutorial
#source activate snakemake-tutorial
#snakemake --help
