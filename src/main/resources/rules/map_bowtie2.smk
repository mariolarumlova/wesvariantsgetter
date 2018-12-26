if config["rules"]["quality_filtering"]:
    INPUT_DEST = "fastq_hq"
elif config["rules"]["removing_adapters"]:
    INPUT_DEST = "fastq_wo_adapters"
else:
    INPUT_DEST = "fastq"

rule bowtie2_map:
    input:
        fq1=INPUT_DEST + "/{sample}1.fastq",
        fq2=INPUT_DEST + "/{sample}2.fastq"
    output:
        "mapped_reads/{sample}.bam"
    shell:
        "bowtie2 -x " + GENOME_REF + " -1 {input.fq1} -2 {input.fq2} | samtools view -Sb - > {output}"
