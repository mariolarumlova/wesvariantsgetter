#!/bin/bash

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

#install fastqc again
conda config --get channels
conda config --remove channels 'conda-forge'
conda install -n env_name -c bioconda fastqc==0.11.5

sudo apt install sharutils

sudo su
apt-get update && apt-get install ssmtp
atom /etc/ssmtp/ssmtp.conf
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
