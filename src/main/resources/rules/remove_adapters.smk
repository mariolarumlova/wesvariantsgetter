rule bbmap_find_adapters:
    input:
        tool=config["miniconda3"]["path"] + "/envs/" + config["env"]["name"] + "/bin/bbmerge.sh",
        fastq1=config["samples"]["path"] + "/{sample}1" + config["samples"]["ext"],
        fastq2=config["samples"]["path"] + "/{sample}2" + config["samples"]["ext"]
    output:
        "adapters/{sample}.fa"
    shell:
        "{input.tool} in1={input.fastq1} in2={input.fastq2} outa={output} strict --tossbrokenreads"

rule fastq_mcf_cut_adapters:
    input:
        "adapters/{sample}.fa",
        config["samples"]["path"] + "/{sample}1" + config["samples"]["ext"],
        config["samples"]["path"] + "/{sample}2" + config["samples"]["ext"]
    output:
        fastq1="fastq_wo_adapters/{sample}1.fastq",
        fastq2="fastq_wo_adapters/{sample}2.fastq"
    shell:
        "fastq-mcf -o {output.fastq1} -o {output.fastq2} {input}"
