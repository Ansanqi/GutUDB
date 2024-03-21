import { createRouter, createWebHashHistory } from "vue-router"

import layout from "@/views/layout/index.vue"
import subLayout from "@/views/layout/subLayout.vue"
import home from "@/views/home/index.vue"

let routes = [
	{
		path: "/",
		redirect: "/home",
	},
	{
		path: "/home",
		children: [
			{
				path: "",
				name: "Home",
				component: home,
			},
			{
				path: "news",
				component: () => import("@/views/home/news.vue"),
			},
			{
				path: "news/article",
				component: () => import("@/views/home/article.vue"),
			},
		],
	},
	{
		path: "/omics",
		component: layout,
		children: [
			{
				path: "",
				name: "Omics",
				component: () => import("@/views/omics/index.vue"),
			},
			{
				path: "project-detail",
				name: "Project",
				component: () => import("@/views/omics/projectDetail.vue"),
			},
			{
				path: "epigen-omics",
				name: "Epigenomics",
				component: subLayout,
				children: [
					{
						path: "dNA-methylation",
						name: "DNA Methylation",
						component: () => import("@/views/omics/dNAMethylation.vue"),
					},
					{
						path: "histone-modification",
						name: "Histone Modification",
						component: () => import("@/views/omics/histoneModification.vue"),
					},
					{
						path: "chromosome-structure",
						name: "Chromosome Structure",
						component: () => import("@/views/omics/chromosomeStructure.vue"),
					},
				],
			},
			{
				path: "genomics",
				name: "Genomics",
				component: subLayout,
				children: [
					{
						path: "genomic-alteration",
						name: "Genomic Alteration",
						component: () => import("@/views/omics/genomics/genomicAlteration.vue"),
					},
					{
						path: "virulence-gene",
						name: "Virulence Gene",
						component: () => import("@/views/omics/genomics/virulenceGene.vue"),
					},
				],
			},
			{
				path: "transcriptomics",
				name: "Transcriptomics",
				component: subLayout,
				children: [
					{
						path: "alternative-splicing",
						name: "Alternative Splicing",
						component: () => import("@/views/omics/transcriptomics/alternativeSplicing.vue"),
					},
					{
						path: "alternative-splicing",
						name: "Alternative  Splicing",
						hidden: true,
						children: [
							{
								path: "data-access",
								name: "Data Access",
								component: () => import("@/views/omics/transcriptomics/dataAccess.vue"),
							},
						],
					},
					{
						path: "gene-expression-Data",
						name: "Gene expression data",
						component: () => import("@/views/omics/transcriptomics/geneExpressionData.vue"),
					},
					{
						path: "non-coding-rNA",
						name: "Non-coding RNA",
						component: () => import("@/views/omics/transcriptomics/nonCodingRNA.vue"),
					},
					{
						path: "RNA-methylation",
						name: "RNA methylation",
						component: () => import("@/views/omics/transcriptomics/rNAMethylation.vue"),
					},
				],
			},
			{
				path: "spatial-omics",
				name: "Spatialomic",
				component: () => import("@/views/omics/spatialOmics.vue"),
			},
			{
				path: "single-cell-omics",
				name: "Single cell omics",
				component: subLayout,
				children: [
					{
						path: "gene-expression",
						name: "Gene expression",
						component: () => import("@/views/omics/geneExpression.vue"),
					},
					{
						path: "gene-expression",
						name: "Gene  expression",
						hidden: true,
						children: [
							{
								path: "details",
								name: "Details",
								component: () => import("@/views/omics/geneDetail.vue"),
							},
						],
					},
					{
						path: "alternative-polvadenylation",
						name: "Alternative polvadenylation",
						component: () => import("@/views/omics/alternativePolvadenylation.vue"),
					},
					{
						path: "alternative-splicing",
						name: "Alternative splicing",
						component: () => import("@/views/omics/alternativeSplicing.vue"),
					},
					{
						path: "Proteomics",
						name: "Proteomics",
						component: () => import("@/views/omics/proteomics.vue"),
					},
				],
			},
			{
				path: "prote-omics",
				name: "Proteomics ",
				component: () => import("@/views/omics/proteomics1.vue"),
			},
			{
				path: "microbi-omics",
				name: "Microbiomics",
				component: subLayout,
				children: [
					{
						path: "associated-genera",
						name: "Associated Genera",
						component: () => import("@/views/omics/associatedGenera.vue"),
					},
					{
						path: "associated-species",
						name: "Associated Species",
						component: () => import("@/views/omics/associatedSpecies.vue"),
					},
				],
			},
			{
				path: "metabol-omics",
				name: "Metabolomics",
				component: () => import("@/views/omics/metabolomics.vue"),
			},
		],
	},
	{
		path: "/gene",
		component: layout,
		children: [
			{
				path: "index",
				name: "Gene",
				component: () => import("@/views/gene/index.vue"),
			},
		],
	},
	{
		path: "/diseases",
		component: layout,
		children: [
			{
				path: "index",
				name: "Intestinal Diseases",
				component: () => import("@/views/home/diseases.vue"),
			},
		],
	},
	{
		path: "/therapy",
		component: layout,
		children: [
			{
				path: "index",
				name: "Therapy",
				component: subLayout,
				children: [
					{
						path: "compounds",
						name: "Chemical Compounds",
						component: () => import("@/views/therapy/compounds.vue"),
					},
					{
						path: "medicine",
						name: "Traditional Medicine",
						component: () => import("@/views/therapy/medicine.vue"),
					},
					{
						path: "probiotics",
						name: "Probiotics",
						component: () => import("@/views/therapy/probiotics.vue"),
					},
				],
			},
		],
	},
	{
		path: "/species",
		component: layout,
		children: [
			{
				path: "index",
				name: "Species",
				component: () => import("@/views/home/species.vue"),
			},
		],
	},
	{
		path: "/chat-doc",
		component: layout,
		children: [
			{
				path: "",
				name: "ChatDoc",
				component: () => import("@/views/chatDoc/index.vue"),
			},
		],
	},
	{
		path: "/statistics",
		name: "Statistics",
		component: () => import("@/views/statistics/index.vue"),
	},
	{
		path: "/faq",
		name: "FAQ",
		component: () => import("@/views/faq/index.vue"),
	},
	{
		path: "/contact",
		name: "Contact US",
		component: () => import("@/views/contact/index.vue"),
	},
]

const router = createRouter({
	history: createWebHashHistory(), // createWebHistory createWebHashHistory
	scrollBehavior: () => ({ top: 0 }),
	routes,
})

router.beforeEach((to, from, next) => {
	if ((to.name === "Gene" || to.name === "Project") && from.name) {
		window.open(`${location.origin + location.pathname + to.href}`)
		return
	}
	next()
})

export default router
