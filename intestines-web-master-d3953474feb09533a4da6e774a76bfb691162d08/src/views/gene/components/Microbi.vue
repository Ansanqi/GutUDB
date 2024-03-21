<script setup>
const props = defineProps({
	id: String,
})
const data = inject("data")
const navList = ["Associated Genera"]
const navIndex = ref(0)
</script>

<template>
	<div class="my-table">
		<el-tabs v-model="navList[0]" :id="props.id + '-0'">
			<el-tab-pane :label="navList[0]" :name="navList[0]">
				<el-table :data="data.generas" border size="default">
					<el-table-column show-overflow-tooltip prop="bodySite" label="Body site" minWidth="100" />
					<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="100" />
					<el-table-column show-overflow-tooltip prop="ifGmrepo" label="ifGmrepo" minWidth="110" />
					<el-table-column show-overflow-tooltip prop="ifMvp" label="ifMvp" minWidth="80">
						<template #default="scope">{{ scope.row.ifMvp || "NA" }}</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="loadedUidNum" label="loaded_uid_num" minWidth="160" />
					<el-table-column show-overflow-tooltip prop="mvpData" label="mvpData" minWidth="150">
						<template #default="scope">{{ scope.row.mvpData || "NA" }}</template>
					</el-table-column>
					<el-table-column show-overflow-tooltip prop="ncbiTaxonId" label="ncbi_taxon_id" minWidth="140" />
					<el-table-column show-overflow-tooltip prop="relativeAbundanceAvg" label="relative_abundance_avg" minWidth="230" />
					<el-table-column show-overflow-tooltip prop="relativeAbundanceMed" label="relative_abundance_med" minWidth="230" />
					<el-table-column show-overflow-tooltip prop="relativeAbundanceStd" label="relative_abundance_std" minWidth="230" />
					<el-table-column show-overflow-tooltip prop="relativeAbundanceSum" label="relative_abundance_sum" minWidth="230" /> </el-table
			></el-tab-pane>
		</el-tabs>
		<!-- <div class="button_wrap">
			<template v-for="(v, index) in navList" :key="v">
				<div class="button" :class="{ active: index === navIndex }" @click="navIndex = index">{{ v }}</div>
			</template>
		</div>
		<template v-if="navIndex === 0">
			<el-table :data="data.generas" border size="default">
				<el-table-column show-overflow-tooltip prop="bodySite" label="Body site" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="100" />
				<el-table-column show-overflow-tooltip prop="generaName" label="Genera name" minWidth="180" />
				<el-table-column show-overflow-tooltip prop="ifGmrepo" label="ifGmrepo" minWidth="110" />
				<el-table-column show-overflow-tooltip prop="ifMvp" label="ifMvp" minWidth="80" />
				<el-table-column show-overflow-tooltip prop="loadedUidNum" label="loaded_uid_num" minWidth="160" />
				<el-table-column show-overflow-tooltip prop="mvpData" label="mvpData" minWidth="150" />
				<el-table-column show-overflow-tooltip prop="ncbiTaxonId" label="ncbi_taxon_id" minWidth="140" />
				<el-table-column show-overflow-tooltip prop="relativeAbundanceAvg" label="relative_abundance_avg" minWidth="230" />
				<el-table-column show-overflow-tooltip prop="relativeAbundanceMed" label="relative_abundance_med" minWidth="230" />
				<el-table-column show-overflow-tooltip prop="relativeAbundanceStd" label="relative_abundance_std" minWidth="230" />
				<el-table-column show-overflow-tooltip prop="relativeAbundanceSum" label="relative_abundance_sum" minWidth="230" />
			</el-table>
		</template> -->
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
