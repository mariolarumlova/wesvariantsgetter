rule bcftools_call:
    input:
        fa=GENOME,
        bam=expand("realigned_reads/{sample}.bam", sample=SAMPLES),
        bai=expand("marked_reads_wg/{sample}.bam.bai", sample=SAMPLES)
    output:
        "calls/variants.vcf"
    shell:
        "samtools mpileup -g -f {input.fa} {input.bam} | "
        "bcftools call -mv - > {output}"
