<script setup>
const props = defineProps({
	id: String,
})
const data = inject("data")

// const navList = ["Chromosome Structure", "DNA Methylation", "Histone Modification"]
const navList = ["DNA Methylation"]
const navIndex = ref(0)
</script>

<template>
	<div class="my-table">
		<el-tabs v-model="navList[0]" :id="props.id + '-0'">
			<el-tab-pane :label="navList[0]" :name="navList[0]">
				<el-table :data="data.dnaMethylations" border size="default">
					<el-table-column show-overflow-tooltip prop="ensembleId" label="Ensemble ID" minWidth="180" />
					<el-table-column show-overflow-tooltip prop="chromosome" label="Chromosome" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="startPosition" label="Start Position" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="endPosition" label="End Position" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="cpgLength" label="CpG Length" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="cpgNumber" label="CpG Number" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="averageMethylationLevel" label="Average Methylation Level" minWidth="250">
						<template #default="scope">{{ scope.row.averageMethylationLevel.toFixed(4) }}</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="strand" label="Strand" minWidth="90" />
					<el-table-column show-overflow-tooltip prop="project" label="Project" minWidth="90">
						<template #default="scope">{{ scope.row.project || "NA" }}</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="sampleId" label="Sample ID" minWidth="110" />
					<el-table-column show-overflow-tooltip prop="species" label="Species" minWidth="90" />
					<el-table-column show-overflow-tooltip prop="genomicsLocation" label="Genomics Location" minWidth="180" />
					<el-table-column show-overflow-tooltip prop="sampleName" label="Sample Name" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="description" label="Description" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="tissueCellLine" label="Tissue/Cell Line" minWidth="160" />
					<el-table-column show-overflow-tooltip prop="gender" label="Gender" minWidth="100" />
					<el-table-column show-overflow-tooltip prop="age" label="Age" minWidth="90" />
					<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="100" />
					<el-table-column show-overflow-tooltip prop="platform" label="Platform" minWidth="100" />
				</el-table>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
