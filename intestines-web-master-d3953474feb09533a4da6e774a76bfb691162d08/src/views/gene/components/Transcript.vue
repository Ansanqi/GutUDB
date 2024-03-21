<script setup>
const props = defineProps({
	id: String,
})
const data = inject("data")
const navList = ["Non-coding RNA"]
const navIndex = ref(0)
const navList1 = ["Non-coding RNA：circRNA", "Non-coding RNA：Inc RNA", "Non-coding RNA：miRNA"]
const active = ref(0)
</script>

<template>
	<div class="my-table">
		<template v-for="(v, index) in navList1">
			<el-tabs v-model="navList1[index]" :id="props.id + '-' + index">
				<el-tab-pane :label="v" :name="v">
					<template v-if="index === 0">
						<el-table :data="data.circRnas" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
							<el-table-column show-overflow-tooltip prop="species" label="Species" min-width="150" />
							<el-table-column show-overflow-tooltip prop="sample" label="Sample" min-width="280" />
							<el-table-column show-overflow-tooltip prop="dysfunctionPattern" label="Dysfunction Pattern" min-width="280" />
							<el-table-column show-overflow-tooltip prop="validatedMethod" label="Validated Method" min-width="240" />
							<el-table-column show-overflow-tooltip prop="description" label="Description" min-width="1000" />
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
						<el-table :data="data.lncRnas" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="120" />
							<el-table-column show-overflow-tooltip prop="species" label="Species" min-width="120" />
							<el-table-column show-overflow-tooltip prop="function" label="Function" min-width="160" />
							<el-table-column show-overflow-tooltip prop="description" label="Description" min-width="500" />
							<el-table-column show-overflow-tooltip prop="chr" label="Chr" min-width="120" />
							<el-table-column show-overflow-tooltip prop="start" label="Start" min-width="120" />
							<el-table-column show-overflow-tooltip prop="end" label="End" min-width="120" />
							<el-table-column show-overflow-tooltip prop="strand" label="Strand" min-width="120" />
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
						<el-table :data="data.miRnas" border size="default">
							<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="140" />
							<el-table-column show-overflow-tooltip prop="expressionPatternOfMiRna" label="Expression pattern of miRNA" min-width="260" />
							<el-table-column show-overflow-tooltip prop="detectionMethodForMiRnaExpression" label="Detection method for miRNA expression" min-width="360" />
							<el-table-column show-overflow-tooltip prop="title" label="Title" min-width="500" />
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
		<div class="my-nav">
			<template v-for="(v, index) in navList1" :key="v">
				<span :class="{ active: index === active }" @click="active = index">{{ v }}</span>
			</template>
		</div>
		<template v-if="active === 0">
			<el-table :data="data.circRnas" border size="default">
				<el-table-column show-overflow-tooltip prop="categories" label="Category" min-width="120" />
				<el-table-column show-overflow-tooltip label="Gene Name" minWidth="120">
					<template #default="scope">
						<router-link :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="150" />
				<el-table-column show-overflow-tooltip prop="species" label="Species" min-width="150" />
				<el-table-column show-overflow-tooltip prop="sample" label="Sample" min-width="280" />
				<el-table-column show-overflow-tooltip prop="dysfunctionPattern" label="Dysfunction Pattern" min-width="280" />
				<el-table-column show-overflow-tooltip prop="validatedMethod" label="Validated Method" min-width="240" />
				<el-table-column show-overflow-tooltip prop="description" label="Description" min-width="1000" />
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
		<template v-else-if="active === 1">
			<el-table :data="data.circRnas" border size="default">
				<el-table-column show-overflow-tooltip prop="categories" label="Category" min-width="120" />
				<el-table-column show-overflow-tooltip prop="ensembleId" label="Ensemble ID" min-width="150" />
				<el-table-column show-overflow-tooltip prop="geneName" label="Gene Name" min-width="200">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="120" />
				<el-table-column show-overflow-tooltip prop="species" label="Species" min-width="120" />
				<el-table-column show-overflow-tooltip prop="function" label="Function Pattern" min-width="160" />
				<el-table-column show-overflow-tooltip prop="description" label="Description Method" min-width="500" />
				<el-table-column show-overflow-tooltip prop="chr" label="Chr" min-width="120" />
				<el-table-column show-overflow-tooltip prop="start" label="Start" min-width="120" />
				<el-table-column show-overflow-tooltip prop="end" label="End" min-width="120" />
				<el-table-column show-overflow-tooltip prop="strand" label="Strand" min-width="120" />
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
			<el-table :data="data.circRnas" border size="default">
				<el-table-column show-overflow-tooltip prop="categories" label="Category" min-width="120" />
				<el-table-column show-overflow-tooltip prop="geneName" label="Gene Name" min-width="150">
					<template #default="scope">
						<span v-if="scope.row.geneName === 'NA' || !scope.row.geneName">{{ scope.row.geneName }}</span>
						<router-link v-else :to="{ path: '/gene/index', query: { geneName: scope.row.geneName } }" class="table-link">{{ scope.row.geneName }}</router-link>
					</template>
				</el-table-column>
				<el-table-column show-overflow-tooltip prop="disease" label="Disease" min-width="140" />
				<el-table-column show-overflow-tooltip prop="expressionPatternOfMiRna" label="Expression pattern of miRNA" min-width="260" />
				<el-table-column show-overflow-tooltip prop="detectionMethodForMiRnaExpression" label="Detection method for miRNA expression" min-width="360" />
				<el-table-column show-overflow-tooltip prop="title" label="Title" min-width="500" />
			</el-table>
		</template> -->
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("../index.scss");
</style>
