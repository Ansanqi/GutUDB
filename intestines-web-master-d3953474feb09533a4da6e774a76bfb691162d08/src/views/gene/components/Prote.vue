<script setup>
const data = inject("data")
</script>

<template>
	<div class="my-table">
		<el-table :data="data.proteomicsList" border  size="default">
			<el-table-column show-overflow-tooltip label="Gene Name" minWidth="110">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
			<el-table-column show-overflow-tooltip prop="disease" label="Disease" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="sapiens" label="Species" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="position" label="Position" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="proteinName" label="Protein Name" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="induction" label="Induction" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="length" label="Length" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="postTranslationalModification" label="Post Translational Modification" minWidth="300" />
			<el-table-column show-overflow-tooltip prop="function" label="Function" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="activityRegulation" label="Activity Regulation" minWidth="220" />
			<el-table-column show-overflow-tooltip prop="pathway" label="Pathway" minWidth="220" />
			<el-table-column prop="pmid" label="PubMed ID" minWidth="7000">
				<template #default="scope">
					<span v-if="scope.row.pmid === 'NA' || !scope.row.pmid">NA</span>
					<template v-else v-for="(v, index) in scope.row.pmid.split(',')">
						<a class="table-link" :href="scope.row.pmidUrl.split(',')[index]" target="_blank">{{ v }}</a
						>,
					</template>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
</style>
