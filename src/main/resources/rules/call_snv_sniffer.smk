rule samtools_generate_header:
    input:
        "realigned_reads/{sample}.bam"
    output:
        "realigned_reads/{sample}_header.sam"
    shell:
        "samtools view -H {input} > {output}"

rule snvsniffer_variant_calling:
    input:
        tool=PROGRAMS_DEST + "SNVSniffer",
        fa=GENOME,
        tumor_header="realigned_reads/" + SAMPLES[0] + "_header.sam",
        normal_header="realigned_reads/" + SAMPLES[1] + "_header.sam",
        tumor="realigned_reads/" + SAMPLES[0] + ".bam",
        normal="realigned_reads/" + SAMPLES[1] + ".bam"
    output:
        "calls/variants.vcf"
    shell:
        "{input.tool} somatic -g {input.fa} -o {output} {input.normal_header} "
        "{input.tumor_header} {input.normal} {input.tumor}"
