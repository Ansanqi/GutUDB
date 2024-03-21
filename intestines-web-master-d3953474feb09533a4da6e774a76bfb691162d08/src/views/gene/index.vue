<script setup>
const route = useRoute()
const { geneName } = route.query
const datas = ref({})
const search = ref("")
const disease = ref("")
const options = ref([])

getDiseaseList()
async function getDiseaseList() {
	const { data } = await api.get("/web/geneDetail/diseaseList")
	options.value = data
}
getList()
async function getList() {
	const { data } = await api.post(`/web/geneDetail/geneDetailByGeneName?geneName=${search.value || geneName}${disease.value ? `&disease=${disease.value}` : ""}`)
	datas.value = data
}
provide("data", datas)

const drugsNav = ref(["Chemical Compounds", "Traditional Medicine"])
const drugsIndex = ref(0)
const drugsCurrentClick = (index) => {
	drugsIndex.value = index
}
import compounds from "./components/Chemical-compounds.vue"
import Medicine from "./components/Chinese-Medicine.vue"
const drugsChildren = [compounds, Medicine]
const drugsComponentName = computed(() => {
	return drugsChildren[drugsIndex.value]
})

const profileNav = ref(["Barplot", "Boxplot&Dotplot", "Survival Analysis"])
const profileIndex = ref(0)
const profileCurrentClick = (index) => {
	profileIndex.value = index
}
import Barplot from "./components/Barplot.vue"
import Dotplot from "./components/Dotplot.vue"
import analysis from "./components/analysis.vue"
const profileChildren = [Barplot, Dotplot, analysis]
const profileComponentName = computed(() => {
	return profileChildren[profileIndex.value]
})

const omicsNav = ref(["Epigenomics", "Genomics", "Transcriptomics", "Spatial Omics", "Singel Cell Omics", "Proteomics", "Microbiomics", "Metabolomics"])
const omicsIndex = ref(0)
const omicsCurrentClick = (index) => {
	omicsIndex.value = index
}
import EpigenOmics from "./components/EpigenOmics.vue"
import GenOmics from "./components/GenOmics.vue"
import Transcript from "./components/Transcript.vue"
import Spatial from "./components/Spatial.vue"
import Singel from "./components/Singel.vue"
import Prote from "./components/Prote.vue"
import Microbi from "./components/Microbi.vue"
import Metabol from "./components/Metabol.vue"
const omicsChildren = [EpigenOmics, GenOmics, Transcript, Spatial, Singel, Prote, Microbi, Metabol]
const omicsComponentName = computed(() => {
	return omicsChildren[omicsIndex.value]
})

const goObj = ref({
	profile: [
		{
			name: "Barplot",
			children: [
				{
					name: "TPM Exp",
					id: "profile-0-0",
				},
				{
					name: "TS Score",
					id: "profile-0-1",
				},
			],
		},
		{
			name: "Boxplot&Dotplot",
			id: "profile-1",
		},
		{
			name: "Survival Analysisi",
			id: "profile-2",
		},
	],
	omics: [
		{
			name: "Epigenomics",
			children: [
				{
					name: "DNA Methylation",
					id: "omics-0-0",
				},
			],
		},
		{
			name: "Genomics",
			children: [
				{
					name: "Genomic Alteration",
					children: [
						{
							name: "CNA Genes",
							id: "omics-1-0",
						},
						{
							name: "Mutated Genes",
							id: "omics-1-1",
						},
						{
							name: "SNP",
							id: "omics-1-2",
						},
						{
							name: "Structural Variant Genes",
							id: "omics-1-3",
						},
						{
							name: "Virulence Gene",
							id: "omics-1-4",
						},
					],
				},
			],
		},
		{
			name: "Transcriptomics",
			children: [
				{
					name: "Non-coding RNA",
					children: [
						{
							name: "circRNA",
							id: "omics-2-0",
						},
						{
							name: "Inc RNA",
							id: "omics-2-1",
						},
						{
							name: "miRNA",
							id: "omics-2-2",
						},
					],
				},
			],
		},
		{ name: "Spatial Omics", id: "omics-3" },
		{
			name: "Singel Cell Omics",
			children: [
				{
					name: "Gene Expression",
					id: "omics-4-0",
				},
				{
					name: "Alternative Splicing",
					id: "omics-4-1",
				},
			],
		},
		{ name: "Proteomics", id: "omics-5" },
		{
			name: "Microbiomics",
			children: [
				{
					name: "Associated Genera",
					id: "omics-6-0",
				},
			],
		},
		{ name: "Metabolomics", id: "omics-7" },
	],
	drugs: [
		{
			name: "Chemical Compounds",
			id: "drugs-0",
		},
		{
			name: "Traditional Medicine",
			id: "drugs-1",
		},
	],
})
const tabsName = ref("profile")
const go = (v, type) => {
	document.querySelector(`${v}`).scrollIntoView(type)
}
</script>

<template>
	<div class="el-backtop-wrap">
		<div class="el-backtop" @click="go('body', true)">
			<i class="el-icon el-backtop__icon">
				<svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
					<path fill="currentColor" d="M512 320 192 704h639.936z"></path>
				</svg>
			</i>
		</div>
		<el-tooltip placement="left">
			<!-- trigger="click" -->
			<template #content>
				<div>
					<el-breadcrumb separator="|">
						<template v-for="v in goObj[tabsName]">
							<el-breadcrumb-item v-if="v.id" @click="go('#' + v.id)" class="sss">{{ v.name }}</el-breadcrumb-item>
							<el-breadcrumb-item v-else>
								{{ v.name }}
								<template v-if="v.children" v-for="m in v.children">
									<div v-if="m.id" @click="go('#' + m.id)" class="sss">{{ m.name }}</div>
									<div v-else>
										{{ m.name }}
										<template v-if="m.children" v-for="n in m.children">
											<div @click="go('#' + n.id)" class="sss">{{ n.name }}</div>
										</template>
									</div>
								</template>
							</el-breadcrumb-item>
						</template>
					</el-breadcrumb>
				</div>
			</template>
			<div class="el-backtop">GO</div>
		</el-tooltip>
		<div class="el-backtop" @click="go('body', false)">
			<i class="el-icon el-backtop__icon">
				<svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
					<path fill="currentColor" d="m192 384 320 384 320-384z"></path>
				</svg>
			</i>
		</div>
	</div>

	<div class="content">
		<div class="top">
			<div class="my-search mb0">
				<el-input v-model="search" placeholder="Search Gene" clearable size="large" @keyup.enter="getList">
						<template #append>
							<span class="iconfont icon-jiyin" @click="getList"></span>
						</template>
					</el-input>
			</div>
			<el-select filterable v-model="disease" class="m-2" placeholder="Select Disease" size="large" @change="getList" clearable>
				<el-option v-for="item in options" :key="item" :label="item" :value="item" />
			</el-select>
		</div>
		<div class="summary">
			<!-- <div class="title">Summary</div> -->
			<div class="con">
				<div class="item">
					<label>Gene Name：</label><b style="color: #ff9900">{{ datas.geneName }}</b>
				</div>
				<div class="item">
					<label>Ensemble ID：</label><b>{{ datas.ensembleId }}</b>
				</div>
				<div class="item">
					<label>Seqnames：</label><b>{{ datas.seqnames }}</b>
				</div>
				<div class="item">
					<label>Start：</label><b>{{ datas.start }}</b>
				</div>
				<div class="item">
					<label>End：</label><b>{{ datas.end }}</b>
				</div>
				<div class="item">
					<label>Strand：</label><b>{{ datas.strand }}</b>
				</div>
				<div class="item">
					<label>
						<el-popover :width="220">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Is it an RNA-binding protein?</template>
						</el-popover>
						RBP：</label
					><b>{{ datas.rbp }}</b>
				</div>
				<div class="item">
					<label>
						<el-popover :width="220">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Is it associated with m6A?</template>
						</el-popover>
						m6A：</label
					><b>{{ datas.m6a }}</b>
				</div>
				<div class="item">
					<label>
						<el-popover :width="300">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Is it associated with alternative splicing?</template>
						</el-popover>
						AS：</label
					><b>{{ datas.asFlag }}</b>
				</div>
				<div class="item">
					<label>
						<el-popover :width="220">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Does RNA editing occur?</template>
						</el-popover>
						RNA edting：</label
					><b>{{ datas.rnaEdting }}</b>
				</div>
				<div class="item">
					<label>
						<el-popover :width="220">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Is it a transcription factor?</template>
						</el-popover>
						TF：</label
					><b>{{ datas.tf }}</b>
				</div>
				<div class="item">
					<label>Motif：</label><b>{{ datas.motif }}</b>
				</div>
				<div class="item mb-0">
					<label>
						<el-popover :width="220">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Is it a therapeutic target?</template>
						</el-popover>
						Therapeutic targets：</label
					><b>{{ datas.therapeuticTargets }}</b>
				</div>
				<div class="item mb-0">
					<label>
						<el-popover :width="220">
							<template #reference><EpQuestionFilled /></template>
							<template #default>Is it a diagnostic target?</template>
						</el-popover>
						Diagnostic targets:</label
					><b>{{ datas.diagnosticTargets }}</b>
				</div>
				<div class="item mb-0"><a :href="datas.ncbiAssociation" target="_blank" class="link_url">NCBI</a></div>
			</div>
		</div>

		<el-tabs type="border-card" v-model="tabsName">
			<el-tab-pane label="profile" name="profile">
				<template v-for="(v, index) in profileNav" :key="v">
					<div class="item" :id="'profile-' + index">
						<div class="title">{{ v }}</div>
						<component :is="profileChildren[index]" :id="'profile-' + index"></component>
					</div>
				</template>
				<!-- <div class="profile">
					<div class="title">Profile</div>
					<div class="my-nav">
						<template v-for="(v, index) in profileNav" :key="v">
							<span :class="{ active: index === profileIndex }" @click="profileCurrentClick(index)">{{ v }}</span>
						</template>
					</div>
					<component :is="profileComponentName"></component>
				</div> -->
			</el-tab-pane>
			<el-tab-pane label="omics" name="omics">
				<template v-for="(v, index) in omicsNav" :key="v">
					<div class="item" :id="'omics-' + index">
						<div class="title">{{ v }}</div>
						<component :is="omicsChildren[index]" :id="'omics-' + index"></component>
					</div>
				</template>
				<!-- <div class="omics">
					<div class="title">Omics</div>
					<div class="my-nav">
						<template v-for="(v, index) in omicsNav" :key="v">
							<span :class="{ active: index === omicsIndex }" @click="omicsCurrentClick(index)">{{ v }}</span>
						</template>
					</div>
					<component :is="omicsComponentName"></component>
				</div> -->
			</el-tab-pane>
			<el-tab-pane label="drugs" name="drugs">
				<template v-for="(v, index) in drugsNav" :key="v">
					<div class="item" :id="'drugs-' + index">
						<div class="title">{{ v }}</div>
						<component :is="drugsChildren[index]"></component>
					</div>
				</template>
				<!--
				<div class="drugs">
					<div class="title">Drugs</div>
					<div class="my-nav">
						<template v-for="(v, index) in drugsNav" :key="v">
							<span :class="{ active: index === drugsIndex }" @click="drugsCurrentClick(index)">{{ v }}</span>
						</template>
					</div>
					<component :is="drugsComponentName"></component>
				</div> -->
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<style lang="scss" scoped>
@import url("@/styles/content.scss");
@import url("./index.scss");
</style>
