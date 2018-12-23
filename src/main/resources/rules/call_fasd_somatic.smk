rule fasd_somatic_variant_calling:
    input:
        tool=PROGRAMS_DEST + "FaSD-somatic/soma",
        fa=GENOME,
        tumor="realigned_reads/" + SAMPLES[0] + ".bam",
        normal="realigned_reads/" + SAMPLES[1] + ".bam"
    output:
        "calls_fasd_somatic/variants.vcf"
    shell:
        "{input.tool} -f {input.fa} -t {input.tumor} -n {input.normal} -o {output}"
