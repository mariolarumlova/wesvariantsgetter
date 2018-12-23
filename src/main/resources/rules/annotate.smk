rule annovar:
    input:
        tool=PROGRAMS_DEST + "annovar/table_annovar.pl",
        db=PROGRAMS_DEST + "annovar/humandb/",
        xref=PROGRAMS_DEST + "annovar/example/gene_fullxref.txt",
        vcf="calls/variants.vcf",
        outname=directory("final_result")
    output:
        "final_result/myanno." + config["genome"]["id"] + "_multianno.csv"
    shell:
        "{input.tool} {input.vcf} {input.db} -buildver " + config["genome"]["id"]
        + " -out {input.outname}myanno -remove "
        "-protocol refGene,cytoBand,exac03,avsnp147,dbnsfp30a "
        "-operation gx,r,f,f,f -nastring . -csvout -polish -xref {input.xref}"
