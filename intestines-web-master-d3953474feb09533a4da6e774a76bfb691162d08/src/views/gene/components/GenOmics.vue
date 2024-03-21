<script setup>
const props = defineProps({
	id: String,
})
const data = inject("data")
const navList = ["Genomic Alteration", "Virulence Gene"]
const navIndex = ref(0)
const navList1 = ref([
	"Genomic Alteration：CNA Genes",
	"Genomic Alteration：Mutated Genes",
	"Genomic Alteration：SNP",
	"Genomic Alteration：Structural Variant Genes",
	"Virulence Gene",
])
const navIndex1 = ref(0)
</script>

<template>
	<div class="my-table">
		<template v-for="(v, index) in navList1">
			<el-tabs v-model="navList1[index]" :id="props.id + '-' + index">
				<el-tab-pane :label="v" :name="v">
					<template v-if="index === 0">
						<el-table :data="data.cnaGenes" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="180" />
							<el-table-column show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="210" />
							<el-table-column show-overflow-tooltip prop="cytoband" label="Cytoband" min-width="120" />
							<el-table-column show-overflow-tooltip prop="can" label="CNA" min-width="120" />
							<el-table-column show-overflow-tooltip prop="freq" label="Freq" min-width="120" />
							<el-table-column prop="pmid" label="PMID" minWidth="600">
								<template #default="scope">
									<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
									<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
										<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
										>{{ index === scope.row.pmid.split(",").length - 1 ? " " : "," }}
									</template>
								</template>
							</el-table-column>
						</el-table>
					</template>
					<template v-else-if="index === 1">
						<el-table :data="data.mutatedGenes" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
							<el-table-column show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="200" />
							<el-table-column show-overflow-tooltip prop="freq" label="Freq" min-width="120" />
							<el-table-column show-overflow-tooltip prop="a5" label="pmid" min-width="120">
								<template #default="scope">
									<a class="table-link" :href="scope.row.pmidUrl" target="_blank">{{ scope.row.pmid }}</a>
								</template>
							</el-table-column>
						</el-table>
					</template>
					<template v-else-if="index === 2">
						<el-table :data="data.snps" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="220" />
							<el-table-column show-overflow-tooltip prop="name" label="Name" min-width="220" />
							<el-table-column show-overflow-tooltip prop="grch38Chromosome" label="GRCh38Chromosome" min-width="150" />
							<el-table-column show-overflow-tooltip prop="grch38Location" label="GRCh38 Location" min-width="130" />
							<el-table-column show-overflow-tooltip prop="conditions" label="Condition" min-width="290">
								<template #default="scope">{{ scope.row.conditions || "NA" }}</template>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="clinicalSignificance" label="Clinical Significance" min-width="250" />
							<el-table-column show-overflow-tooltip prop="reviewStatus" label="Review Status" min-width="200" />
							<el-table-column show-overflow-tooltip prop="proteinChange" label="Protein Change" min-width="150" />
							<el-table-column show-overflow-tooltip prop="accession" label="Accession" min-width="130" />
						</el-table>
					</template>
					<template v-else-if="index === 3">
						<el-table :data="data.Structural" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
							<el-table-column show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="200" />
							<el-table-column show-overflow-tooltip prop="freq" label="Freq" min-width="120" />
							<el-table-column prop="pmid" label="PMID" minWidth="600">
								<template #default="scope">
									<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
									<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
										<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
										>{{ index === scope.row.pmid.split(",").length - 1 ? " " : "," }}
									</template>
								</template>
							</el-table-column>
						</el-table>
					</template>
					<template v-else>
						<el-table :data="data.virulenceGenes" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="120" />
							<el-table-column show-overflow-tooltip prop="location" label="Location" min-width="100" />
							<el-table-column show-overflow-tooltip prop="phenotype" label="Phenotype" min-width="200" />
							<el-table-column show-overflow-tooltip prop="inheritance" label="Inheritance" min-width="120" />
							<el-table-column show-overflow-tooltip prop="phenotypeMappingKey" label="Phenotype Mapping Key" min-width="150" />
						</el-table>
					</template>
				</el-tab-pane>
			</el-tabs>
		</template>
		<!-- <div class="button_wrap">
			<template v-for="(v, index) in navList" :key="v">
				<div class="button" :class="{ active: index === navIndex }" @click="navIndex = index">{{ v }}</div>
			</template>
		</div>
		<div class="my-nav" v-if="navIndex === 0">
			<template v-for="(v, index) in navList1" :key="v">
				<span :class="{ active: index === navIndex1 }" @click="navIndex1 = index">{{ v }}</span>
			</template>
		</div>
		<template v-if="navIndex === 0">
			<template v-if="navIndex1 === 0">
				<el-table :data="data.cnaGenes" border size="default">
					<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
						<template #default="scope">
							<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
						</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
					<el-table-column show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="200" />
					<el-table-column show-overflow-tooltip prop="cytoband" label="Cytoband" min-width="120" />
					<el-table-column show-overflow-tooltip prop="can" label="CNA" min-width="120" />
					<el-table-column show-overflow-tooltip prop="freq" label="Freq" min-width="120" />
					<el-table-column prop="pmid" label="PMID" minWidth="600">
						<template #default="scope">
							<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
							<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
								<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
								>{{ index === scope.row.pmid.split(",").length - 1 ? " " : "," }}
							</template>
						</template>
					</el-table-column>
				</el-table>
			</template>
			<template v-else-if="navIndex1 === 1">
				<el-table :data="data.mutatedGenes" border size="default">
					<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
						<template #default="scope">
							<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
						</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
					<el-table-column show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="200" />
					<el-table-column show-overflow-tooltip prop="freq" label="Freq" min-width="120" />
					<el-table-column show-overflow-tooltip prop="a5" label="pmid" min-width="120">
						<template #default="scope">
							<a class="table-link" :href="scope.row.pmidUrl" target="_blank">{{ scope.row.pmid }}</a>
						</template>
					</el-table-column>
				</el-table>
			</template>
			<template v-else-if="navIndex1 === 2">
				<el-table :data="data.snps" border size="default">
					<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
						<template #default="scope">
							<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
						</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
					<el-table-column show-overflow-tooltip prop="name" label="Name" min-width="200" />
					<el-table-column show-overflow-tooltip prop="grch38Chromosome" label="GRCh38Chromosome" min-width="200" />
					<el-table-column show-overflow-tooltip prop="grch38Location" label="GRCh38 Location" min-width="200" />
					<el-table-column show-overflow-tooltip prop="condition" label="Condition" min-width="120" />
					<el-table-column show-overflow-tooltip prop="clinicalSignificance" label="Clinical Significance" min-width="200" />
					<el-table-column show-overflow-tooltip prop="reviewStatus" label="Review Status" min-width="140" />
					<el-table-column show-overflow-tooltip prop="proteinChange" label="Protein Change" min-width="150" />
					<el-table-column show-overflow-tooltip prop="accession" label="Accession" min-width="130" />
				</el-table>
			</template>
			<template v-else>
				<el-table :data="data.Structural" border size="default">
					<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
						<template #default="scope">
							<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
						</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
					<el-table-column show-overflow-tooltip prop="isCancerGeneSourceOncoKb" label="Is Cancer Gene (source: OncoKB)" min-width="200" />
					<el-table-column show-overflow-tooltip prop="freq" label="Freq" min-width="120" />
					<el-table-column prop="pmid" label="PMID" minWidth="600">
						<template #default="scope">
							<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
							<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
								<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
								>{{ index === scope.row.pmid.split(",").length - 1 ? " " : "," }}
							</template>
						</template>
					</el-table-column>
				</el-table>
			</template>
		</template>
		<template v-else>
			<el-table :data="data.virulenceGenes" border size="default">
				<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="120" />
				<el-table-column show-overflow-tooltip prop="location" label="Location" min-width="100" />
				<el-table-column show-overflow-tooltip prop="phenotype" label="Phenotype" min-width="200" />
				<el-table-column show-overflow-tooltip prop="inheritance" label="Inheritance" min-width="120" />
				<el-table-column show-overflow-tooltip prop="phenotypeMappingKey" label="Phenotype Mapping Key" min-width="150" />
			</el-table>
		</template> -->
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
