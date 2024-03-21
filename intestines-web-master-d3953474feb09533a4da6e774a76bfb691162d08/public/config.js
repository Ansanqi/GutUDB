window.config = {
	baseUrl: "http://47.109.69.209:8314/intestinedb", // 测试服务器地址
	// baseUrl: "http://172.16.15.33:9088/family-ultrasound", // 本地地址
	imgUrl: "http://47.109.69.209:8314/intestinedb", //图片存储地址
	timeout: 60000, // 去掉3个0 才是秒
	// 搜索条件配置
	serchObj: {
		'DNA Methylation':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
			{
				type: 'input',
				name: "sampleId",
				placeholder: "Search Sample ID"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Colorectal Adenocarcinoma","Conlon cancer","Healthy","Inflame","Iron deficiency anemia@@Bipolar disorder","Normal"]
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Bos taurus","Danio rerio","Gallus gallus","Homo sapiens","Mus musculus"]
			},
			{
				type: 'select',
				name: "tissueCellLine",
				placeholder: "Select Tissue/Cell Line",
				list: ["Colon","Colon sigmoideum","Ileum","Intestine","Jejunum","Large intestine","Small intestine","SW-480 cell","SW-620 cell"]
			},
		],
		'Histone Modification':[
			{
				type: 'select',
				name: "histone",
				placeholder: "Select Histone",
				list: ["H3K27ac","H3K27me3","H3K36me3","H3K4me1","H3K4me3","H3K9me3"]
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Homo sapiens","Mus musculus"]
			},
			{
				type: 'select',
				name: "cellLineTissue",
				placeholder: "Select Cell Line/Tissue",
				list: ["sigmoid colon","small intestine","transverse colon"]
			},
		],
		'Chromosome Structure':[
			{
				type: 'input',
				name: "fileName",
				placeholder: "Search File Name"
			}
		],
		'Genomic Alteration':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Appendiceal Cancer","Colon Cancer","Colorectal Adenocarcinoma","Colorectal Cancer","Disparities in metastatic colorectal cancer between Africans and Americans","Metastatic Colorectal Cancer","Rectal Cancer"]
			},
			{
				type: 'select',
				name: "cna",
				placeholder: "Select CNA",
				list: ["AMP","HOMDEL"]
			},
		],
		'cnaGenes':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Appendiceal Cancer","Colon Cancer","Colorectal Adenocarcinoma","Colorectal Cancer","Disparities in metastatic colorectal cancer between Africans and Americans","Metastatic Colorectal Cancer","Rectal Cancer"]
			},
			{
				type: 'select',
				name: "cna",
				placeholder: "Select CNA",
				list: ["AMP","HOMDEL"]
			},
		],
		'mutatedGenes':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Appendiceal Cancer","Colon Adenocarcinoma","Colon Cancer","Colorectal Adenocarcinoma","Colorectal Adenocarcinoma Triplets","Colorectal Cancer","Disparities in metastatic colorectal cancer between Africans and Americans","Metastatic Colorectal Cancer","Rectal Cancer"]
			},
		],
		'snp':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Ascariasis (parasitic roundworm Ascaris lumbricoides)","Carcinoid (also carcinoid tumor)","Cholera","Chronic functional abdominal pain","Coeliac disease (autoimmune disorder of the small intestine)","Colitis (inflammation of the colon)","Colon bleeding (hemorrhage)","Colon cancer","Colon polyps","Constipation (also known as costiveness, dyschezia, and dyssynergic defecation)","Crohn's disease","Diarrhea (also spelled diarrhoea)","Gastric dumping syndrome (rapid gastric emptying)","Giardiasis (or beaver fever)","Hirschsprung's disease (congenital aganglionic megacolon)","Ileus (bowel obstruction)","Inflammatory bowel disease","Intestinal obstruction","Intussusception","Irritable bowel syndrome (IBS or spastic colon)","Lakeworm infection","Meckel's diverticulum (congenital diverticulum)","Pseudomembranous colitis","Rectal cancer","Rectal prolapse","Small intestine cancer","Stomach flu (enteritis)","Tropical sprue"]
			},
		],
		'structuralVariantGenes':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Appendiceal Cancer","Colorectal Adenocarcinoma","Colorectal Cancer","Metastatic Colorectal Cancer"]
			},
		],
		'Virulence Gene':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Colorectal cancer","Congenital bowel disease","Congenital megacolon","Congenital short bowel syndrome","Enterorrhagid","Gastrointestinal interstitial syndrome","Gastrointestinal stromal tumor","Hamartoma","Inflammatory bowel disease","Intestinal atresia","Intestinal dyskinesia","Lynch syndrome","Mismatch repair cancer syndrome","polyposis","polyposis cancer syndrome","Spontaneous intestinal rupture"]
			},
		],
		'Alternative Splicing':[
			{
				type: 'select',
				name: "dataAccessId",
				placeholder: "Select Data Access ID",
				list: ["GSE148696","GSE149312","GSE154936","GSE157059","GSE159201","GSE162899"]
			},
			{
				type: 'select',
				name: "condition",
				placeholder: "Select Condition",
				list: ["Lastingness of infection","Status","Treatment"]
			},
			{
				type: 'select',
				name: "cellLineTissueOrganiod",
				placeholder: "Select Cell line/Tissue/Organiod",
				list: ["Cell line(Colon)","Cell line(gut)","Organiod","Tissue"]
			},
			{
				type: 'select',
				name: "tissue",
				placeholder: "Select Tissue",
				list: ["Cell line","hPSC-derived colon organoids","Ileum","Intestinal organoids","iPSC-derived intestinal organoids"]
			},
			{
				type: 'select',
				name: "typeCategory",
				placeholder: "Select Type_category",
				list: ["Cell line","Organiod","Tissue"]
			},
			{
				type: 'select',
				name: "cellType",
				placeholder: "Select Cell Type",
				list: ["Caco-2","HCT116","Hesc cells","HT29","NA"]
			},
		],
		'sraRun':[
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["control","COVID-19","SARS-CoV"]
			},
			{
				type: 'select',
				name: "cellType",
				placeholder: "Select Cell Type",
				list: ["Caco-2","HCT116","Hesc cells","HT29","NA"]
			},
			{
				type: 'select',
				name: "tissue",
				placeholder: "Select Tissue",
				list: ["cell line","Colon","Ileum","Intestinal organoids","iPSC-derived intestinal organoids","lung tissue"]
			},
			{
				type: 'select',
				name: "strain",
				placeholder: "Select Strain",
				list: ["Alpha","NA"]
			},
		],
		'a3SS':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
		],
		'a5SS':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
		],
		'mXE':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
		],
		'rI':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
		],
		'sE':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
		],
		'Gene expression data':[
			{
				type: 'input',
				name: "project",
				placeholder: "Search Project"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["Acute colitis","Colitis","Colitis、Colon Cancer","Colitis、Colorectal cancer","Colon adenocarcinoma","Colon cancer","Colon carcinoma","Colon Damage","Colon tumor","Colonic mucosal injury","Colorectal cancer","Colorectal tumorigenesis","Crohn's disease","Crohn's disease、Ulcerative colitis","Crohn’s disease","Crohn’s disease 、Ulcerative colitis","CVID","Familial AdenomatousPolyposis","Helminth infection","Inflammatory bowel disease (IBD)、Crohn's disease","Inflammatory bowel diseases (IBD)","Inflammatory bowel diseases (IBDs)","Intestinal inflammation、Colitis","Intestinal ischemic injury","Lymphocytic colitis","NA","Normal","Rectal cancer","Ulcerative colitis"]
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Danio rerio","Homo sapiens","Macaca mulatta","Mus musculus","Ovis aries","Sus scrofa"]
			},
			{
				type: 'select',
				name: "tissueCellLine",
				placeholder: "Select Tissue/Cell line",
				list: ["Colon","Colorectal","DKO-1","HCT116","Intestinal epithelial organoid","Intestinal stem cell","M2 polarized macrophages","Regulatory T cells","B cells","Blood-derived macrophages","Caecum、SW480","CC14 primary colon cancer cells","Colon","Colon adenocarcinoma tissue、CSF-1R- TAMs","Colon cancer tissue","Colon epithelial cells","Colon fibroblast","Colon Inflamed mucosa","Colon neoplasm tissue","Colon tissue","Colon、Colon crypt epithelial cells","Colon、CRC spheroid cells","Colon、HCT116","Colon、LoVo human colorectal cancer cell line","Colonic Epithelial Cells","Colonic epithelium","Colonic macrophages","Colonic mononuclear phagocytes in mice","Colorectal","Colorectal cancer tissue","Colorectal tissue","Colorectal tumors tissue","Colorectum","CT26 solid tumors","Duodenal","Duodenum","HCT-8","HCT116","HT29","Human colorectal cancer cell line","Ileum、Rectum","Intestinal fibroblast cell","Intestinal Progenitors","Jejunal","Jejunual mucosa","Jejunum","Jejunum、Ileum、Cecum、Colon","Large Intestine、EC cell","Macrophage","Non-neoplasitc colon epitheila cells","Precancerous intestinal lesions","Pylorus、Duodenum、Jejunum、Ileum、Colon","Rectal cancer tissue","Sigmoid","Sigmoid colon","Sigmoid colon mucosa","Sigmoid colon、Terminal ileum、Caco-2","SW403","Sw480 cell line","SW480 colon cancer cell","SW620","Whole embryo"]
			},
		],
		'circRNA':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ['colon cancer','colorectal cancer']
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ['Homo sapiens']
			},
		],
		'incRNA':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["colon cancer","colorectal cancer","Crohn's disease","enterovirus 71 infection","gastrointestinal cancer","gastrointestinal stromal tumor","H.pylori-related diseases","hepatic colorectal metastasis","Hirschsprung's disease","ulcerative colitis"]
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Human", 'Mouse']
			},
		],
		'miRNA':[
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Diseases",
				list: ["colorectal cancer"]
			},
		],
		'RNA methylation':[
			{
				type: 'select',
				name: "project",
				placeholder: "Select Project",
				list: ["GSE128699","GSE74771"]
			},
			{
				type: 'select',
				name: "seqnames",
				placeholder: "Select Seqnames",
				list: ["chr1","chr10","chr11","chr12","chr13","chr14","chr15","chr16","chr17","chr18","chr19","chr2","chr20","chr21","chr22","chr3","chr4","chr5","chr6","chr7","chr8","chr9","chrM","chrX","chrY"]
			},
			{
				type: 'select',
				name: "modification",
				placeholder: "Select Modification",
				list: ["m5C","m6A"]
			},
			{
				type: 'select',
				name: "condition",
				placeholder: "Select Condition",
				list: ["control","Ctrl","METTL5_Knockout","ZCCHC4_Knockout"]
			},
			{
				type: 'select',
				name: "technique",
				placeholder: "Select Technique",
				list: ["Bisulfite-seq","miCLIP"]
			},
		],
		'Spatialomic': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Search Species",
				list: ["Human", 'Mouse']
			},
			{
				type: 'select',
				name: "tissue",
				placeholder: "Select Tissue",
				list: ["colorectal carcinoma", "ileum", "Intestine"],
			},
			{
				type: 'select',
				name: "biotechCategories",
				placeholder: "Select Biotech Categories",
				list: ["Spatial Proteomics", "Spatial Transcriptomics"],
			},
			{
				type: 'select',
				name: "biotech",
				placeholder: "Select Biotech",
				list: ["10X Visium", "CODEX"],
			}
		],
		'Details': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene,Name"
			},
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
			{
				type: 'select',
				name: "tissue",
				placeholder: "Select Tissue",
				list: ["colon", "colorectal"],
			},
			{
				type: 'select',
				name: "cancerType",
				placeholder: "Select Cancer Type",
				list: ["colorectal adenocarcinoma (COAD)", "colorectal adenocarcinoma (COAD)", "melanoma (MEL)"],
			},
			{
				type: 'select',
				name: "treatmentType",
				placeholder: "Select Treatment Type",
				list: ["immunotherapy", "naive", "unknown"],
			}
		],
		'Alternative polvadenylation': [
			{
				type: 'input',
				name: "ensembleId",
				placeholder: "Search Ensemble ID"
			},
			{
				type: 'input',
				name: "sampleId",
				placeholder: "Search Sample ID"
			},
			{
				type: 'select',
				name: "project",
				placeholder: "Select Project",
				list: ["GSE113043", "GSE114374", "GSE92865"],
			},
			{
				type: 'select',
				name: "organism",
				placeholder: "Select Organism",
				list: ["Homo sapiens (GRCh38)", "Mus musculus (mm10)"],
			},
			{
				type: 'select',
				name: "tissue",
				placeholder: "Select Tissue",
				list: ["Colon", "Small intestine"],
			},
			{
				type: 'select',
				name: "condition",
				placeholder: "Select Condition",
				list: ["control cell population depleted of ISCs without perturbation", "control intestinal stem cell population without perturbation", "Healthy", "NA", "Rspo LOF", "Rspo1 GOF", "RSPO2 GOF", "Ulcerative Colitis", "Wnt GOF", "Wnt LOF"],
			}
		],
		'Alternative splicing': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "cluster1",
				placeholder: "Select Cluster1",
				list: ["C01_CD8.LEF1", "C02_CD8.CX3CR1", "C03_CD8.SLC4A10", "C05_CD8.GZMK", "C07_CD4.FOXP3", "C08_CD4.CTLA4", "C09_CD4.GZMA", "C10_CD4.CXCL13", "C11_CD4.GNLY"],
			},
			{
				type: 'select',
				name: "cluster2",
				placeholder: "Select Cluster2",
				list: ["C01_CD8.LEF1", "C02_CD8.CX3CR1", "C03_CD8.SLC4A10", "C04_CD8.LAYN", "C05_CD8.GZMK", "C06_CD4.CCR7", "C07_CD4.FOXP3", "C09_CD4.GZMA", "C10_CD4.CXCL13", "C11_CD4.GNLY"],
			}
		],
		'Proteomics': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search"
			}
		],
		'Proteomics ': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene,Protein"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: ["ABCD syndrome", "Celiac disease", "Chylomicron retention disease", "Colorectal cancer", "Congenital short bowel syndrome", "Cowden syndrome", "Diarrhea 5, with tufting enteropathy, congenital", "Familial adenomatous polyposis", "Feingold syndrome 1", "Hereditary non-polyposis colorectal cancer", "Inflammatory bowel disease", "Juvenile polyposis syndrome", "Meconium ileus", "Sessile serrated polyposis cancer syndrome", "Townes-Brocks syndrome", "Trehalase deficiency"],
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Homo sapiens"],
			},
			{
				type: 'select',
				name: "position",
				placeholder: "Select Position",
				list: ["anus", "colon", "duodenum", "ileum", "large intestine", "rectum", "Small intestine"],
			},
		],
		'Associated Genera': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Genera"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: ["Crohn Disease", "Healthy", "Irritable Bowel Syndrome", "Necrotizing Enterocolitis", "Pouchitis"],
			},
			{
				type: 'select',
				name: "bodySite",
				placeholder: "Select Body site",
				list: ["Cecum", "Colon", "Duodenum", "Rectum"],
			},
		],
		'Associated Species': [
			{
				type: 'input',
				name: "speies",
				placeholder: "Search Speies"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: ["Healthy", "Irritable Bowel Syndrome", "Pouchitis"],
			},
			{
				type: 'select',
				name: "bodySite",
				placeholder: "Select Body site",
				list: ["Cecum", "Colon", "Duodenum", "Rectum"],
			},
		],
		'Metabolomics': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene,Sample Type"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: ["Acquired Immunodeficiency Syndrome","Colon adenomas","Colon polyps","Colorectal cancer","COVID-19","Crohn's Disease","HIV infection","Inflammatory Bowel Disease","Irritable Bowel Syndrome","NA","Short Bowel Syndrome","Ulcerative Colitis"],
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Human", "Mouse", "NA"],
			},
			{
				type: 'select',
				name: "alteration",
				placeholder: "Select Alteration",
				list: ["activation", "inhibition", "NA"],
			},
		],
		'Intestinal Diseases': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
		],
		'Chemical Compounds': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: []
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ["Human", "Mouse"]
			},
		],
		'Traditional Medicine': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: ["Abnormality Of The Anus", "Aganglionic Megacolon", "Bowel Diverticulosis", "Bowel Incontinence", "Chronic Ileitis", "Colitis", "Colon Adenocarcinoma", "Colon Cancer", "Colonic Diverticula", "Colonic Neoplasms", "Colorectal Cancer", "Colorectal Neoplasms", "Decreased Small Intestinal Mucosa Lactase Activity", "Duodenal Ulcer", "Enterocolitis", "Functional Intestinal Obstruction", "Hereditary Nonpolyposis Colorectal Carcinoma", "Hereditary Nonpolyposis Colorectal Neoplasms", "Intestinal Obstruction", "Irritable Bowel Syndrome", "Malrotation Of Small Bowel", "Meconium Ileus", "Metastatic Colorectal Cancer", "Microcolon", "Neoplasm Of The Colon", "Opioid-Induced Bowel Dysfunction", "Paralytic Ileus", "Rectal Abscess", "Rectal Prolapse", "Ulcerative Colitis"]
			},
		],
		'Probiotics': [
			{
				type: 'select',
				name: "probiotics",
				placeholder: "Select Probiotics",
				list: ["Bacillus coagulans", "Bacillus subtilis", "Bifidobacterium animalis subsp.lactis", "Bifidobacterium bifidum", "Bifidobacterium breve", "Bifidobacterium infantis", "Bifidobacterium longum subsp.longum", "Enterrococus faecium", "Lactobacillus acidophilus", "Lactobacillus brevis", "Lactobacillus bulgaricus", "Lactobacillus casei", "Lactobacillus gasseri", "Lactobacillus helveticus", "Lactobacillus paracasei", "Lactobacillus plantarum", "Lactobacillus rhamnosus", "Lactobacillus salivarius", "Lactococcus lactis", "Pediococcus acidilactici", "Saccharomyces boulardii", "Streptococcus thermophilus"]
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: ["Accute diarrhoea", "Acute diarrhea,Recurrent Clostridium difficile infection,Irritable bowel syndrome,Irritable bowel syndrome,Travelers’ diarrhea,Antibiotic-associated diarrhea,HIV/AIDS-associated diarrhea", "acute diarrhoea", "Behcet's syndrome", "diarrhea,allergies,gas,irritable bowel syndrome,yeast infections", "gastrointestinal infections", "irritable bowel syndrome", "Lactose intolerance,Antibiotic associated diarrhoea,C difficile associated diarrhoea", "NA", "pediatric diarrhea", "Spontaneously Hypertensive"]
			},
			{
				type: 'select',
				name: "genus",
				placeholder: "Select Genus",
				list: ["Bacillus", "Bacillus natto", "Bifidobacterium", "Bifidobacterium animalis subsp animalis and Bifidobacterium animalis subsp lactis", "Enterococcus", "Lactobacillus", "Lactococcus", "Pediococcus", "Saccharomyces", "Streptococcus"]
			},
			{
				type: 'select',
				name: "location",
				placeholder: "Select Location",
				list: ["digestive tract", "digestive tract of human infants", "duodenal、jejunal", "gastrointestinal tract", "gastrointestinal tract、mouth、and vagina", "gastrointestinal tract、vagina", "gastrointestinal tracts", "intestine", "intestine、mouth", "intestine、vagina", "large intestines", "NA", "saliva、gastrointestinal tract"]
			},
		],
		'Species': [
			{
				type: 'input',
				name: "keywords",
				placeholder: "Search Gene"
			},
			{
				type: 'select',
				name: "species",
				placeholder: "Select Species",
				list: ['Human', 'Mouse']
			},
			{
				type: 'select',
				name: "disease",
				placeholder: "Select Disease",
				list: []
			},
		]
	}
}
// {
// 	type: 'input',   							input 输入框  select 下拉框
// 	name: "keywords",							传给后端的字段name
// 	placeholder: "Search Gene"		提示词
// 	list: ['Human', 'Mouse']			下拉框选项
// },
