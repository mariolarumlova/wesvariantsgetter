if config["rules"]["quality_filtering"]:
    INPUT_DEST = "fastq_hq"
elif config["rules"]["removing_adapters"]:
    INPUT_DEST = "fastq_wo_adapters"
else:
    INPUT_DEST = "fastq"

rule bwa_map:
    input:
        GENOME,
        fq1=INPUT_DEST + "/{sample}1" + config["samples"]["ext"],
        fq2=INPUT_DEST + "/{sample}2" + config["samples"]["ext"]
    output:
        "mapped_reads/{sample}.bam"
    shell:
        "bwa mem {input} | samtools view -Sb - > {output}"
