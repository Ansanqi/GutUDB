<script setup>
import * as echarts from "echarts"

const active = ref(0)

const arr = ref(["Data Statistics", "Genomics", "Epigenomics", "Metabolomics", "Proteomics", "Microbiome", "Therapy", "Transcriptomics", "Single cell omics"])

const data1_t = ["Engine", "Direct", "Email", "Union", "Video"]
const data1_v = [1048, 735, 580, 484, 300]

let data1, data2, data3, data4, data5, data6, data7, data8, data9

onMounted(() => {
	initEcharts(0)
})

onUnmounted(() => {
	clearOther()
})

const changeMenu = (index) => {
	active.value = index
	nextTick(() => {
		initEcharts(index)
	})
}

function cSetOption(div, data_t, data_v, type = 1) {
	if (type == 1) {
		const data = data_t.map((item, index) => {
			return {
				name: item,
				value: data_v[index],
			}
		})
		const obj = {
			tooltip: {
				trigger: "item",
				formatter: "{b} : {c} ({d}%)",
			},
			// legend: {
			// 	orient: "vertical",
			// 	left: "20",
			// 	top: "center",
			// },
			series: [
				{
					type: "pie",
					radius: "80%",
					center: ["55%", "50%"],
					data,
				},
			],
		}
		// if (div == c1_1) {
		// 	delete obj.legend
		// }
		div.setOption(obj, true)
	} else {
		const obj = {
			grid: {
				x: 110,
				y: 20,
				x2: 20,
				y2: 30,
			},
			tooltip: {},
			yAxis: {
				data: data_t,
			},
			xAxis: {},
			series: [
				{
					type: "bar",
					data: data_v,
					itemStyle: {
						color: "#00c0c9",
					},
				},
			],
		}
		if (type == 3) {
			const maxLength = 20
			obj.grid.x = 160
			obj.yAxis.axisLabel = {
				interval: 0,
				formatter: function (value) {
					// 如果标签长度超过4，将剩余的文字替换为省略号
					if (value.length > maxLength) {
						return value.slice(0, maxLength) + "..."
					} else {
						return value
					}
				},
			}
		}
		div.setOption(obj, true)
	}
}

let c1_1,
	c1_2,
	c1_3,
	c1_4,
	c2_1,
	c2_2,
	c2_3,
	c2_4,
	c2_5,
	c2_6,
	c3_1,
	c3_2,
	c4_1,
	c4_2,
	c5_1,
	c5_2,
	c6_1,
	c6_2,
	c6_3,
	c6_4,
	c7_1,
	c7_2,
	c7_3,
	c7_4,
	c7_5,
	c7_6,
	c8_1,
	c8_2,
	c8_3,
	c8_4,
	c9_1,
	c9_2

function clearOther() {
	c1_1?.dispose()
	c1_2?.dispose()
	c1_3?.dispose()
	c1_4?.dispose()
	c2_1?.dispose()
	c2_2?.dispose()
	c2_3?.dispose()
	c2_4?.dispose()
	c2_5?.dispose()
	c2_6?.dispose()
	c3_1?.dispose()
	c3_2?.dispose()
	c4_1?.dispose()
	c4_2?.dispose()
	c5_1?.dispose()
	c5_2?.dispose()
	c6_1?.dispose()
	c6_2?.dispose()
	c6_3?.dispose()
	c6_4?.dispose()
	c7_1?.dispose()
	c7_2?.dispose()
	c7_3?.dispose()
	c7_4?.dispose()
	c7_5?.dispose()
	c7_6?.dispose()
	c8_1?.dispose()
	c8_2?.dispose()
	c8_3?.dispose()
	c8_4?.dispose()
	c9_1?.dispose()
	c9_2?.dispose()
}

function changeData(titleArray, valueArray) {
	return {
			t: titleArray,
			v: valueArray,
		}
	if (titleArray.length != valueArray.length) {
		console.error("titleArray valueArray 长度不同")
		return {
			t: titleArray,
			v: valueArray,
		}
	}
	const sum = valueArray.reduce((p, c) => p + c)

	const tArr = []
	const vArr = []

	let otherSum = 0

	for (let i = 0, len = titleArray.length; i < len; i++) {
		const title = titleArray[i]
		const value = valueArray[i]

		if (value / sum < 0.01) {
			otherSum = otherSum + value
		} else {
			tArr.push(title)
			vArr.push(value)
		}
	}
	if (otherSum != 0) {
		tArr.push("other")
		vArr.push(otherSum)
	}
	return {
		t: tArr,
		v: vArr,
	}
}
async function initEcharts(index) {
	clearOther()

	const loading = ElLoading.service({
		lock: true,
		text: "Loading",
		background: "rgba(0, 0, 0, 0.7)",
	})
	switch (index) {
		case 0:
			{
				c1_1 = echarts.init(document.getElementById("c1_1"))
				c1_2 = echarts.init(document.getElementById("c1_2"))
				c1_3 = echarts.init(document.getElementById("c1_3"))
				c1_4 = echarts.init(document.getElementById("c1_4"))

				try {
					let data
					if (!data1) {
						const res = await api.get("/web/statistics/dataStatistics")
						data = res.data
						data1 = data
					} else {
						data = data1
					}
					const obj1 = data.intestinalDiseases
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)

					const obj2 = data.position
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					const obj3 = data.species
					const data3_t = Object.keys(obj3)
					const data3_v = Object.values(obj3)
					const obj4 = Object.fromEntries(Object.entries(data.top10Genes).sort(([, val1], [, val2]) => val1 - val2))
					const data4_t = Object.keys(obj4)
					const data4_v = Object.values(obj4)
					cSetOption(c1_1, data1_t, data1_v)
					cSetOption(c1_2, data2_t, data2_v)
					cSetOption(c1_3, data3_t, data3_v)
					cSetOption(c1_4, data4_t, data4_v, 2)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 1:
			{
				c2_1 = echarts.init(document.getElementById("c2_1"))
				// c2_2 = echarts.init(document.getElementById("c2_2"))
				// c2_3 = echarts.init(document.getElementById("c2_3"))
				c2_4 = echarts.init(document.getElementById("c2_4"))
				// c2_5 = echarts.init(document.getElementById("c2_5"))
				// c2_6 = echarts.init(document.getElementById("c2_6"))

				try {
					let data
					if (!data2) {
						const res = await api.get("/web/statistics/genomics")
						data = res.data
						data2 = data
					} else {
						data = data2
					}
					const obj1 = data.genomicAlteration
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)

					const obj2 = Object.fromEntries(Object.entries(data.top10CnaGenes).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					const obj3 = Object.fromEntries(Object.entries(data.top10MutatedGenes).sort(([, val1], [, val2]) => val1 - val2))
					const data3_t = Object.keys(obj3)
					const data3_v = Object.values(obj3)
					const obj4 = Object.fromEntries(Object.entries(data.top10Snp).sort(([, val1], [, val2]) => val1 - val2))
					const data4_t = Object.keys(obj4)
					const data4_v = Object.values(obj4)
					const obj5 = Object.fromEntries(Object.entries(data.top10StructuralVariantGenes).sort(([, val1], [, val2]) => val1 - val2))
					const data5_t = Object.keys(obj5)
					const data5_v = Object.values(obj5)
					const obj6 = Object.fromEntries(Object.entries(data.top10VirulenceGenes).sort(([, val1], [, val2]) => val1 - val2))
					const data6_t = Object.keys(obj6)
					const data6_v = Object.values(obj6)

					cSetOption(c2_1, data1_t, data1_v)
					// cSetOption(c2_2, data2_t, data2_v, 2)
					// cSetOption(c2_3, data3_t, data3_v, 2)
					cSetOption(c2_4, data4_t, data4_v, 2)
					console.log(data4_t)
					console.log(data4_v)
					// cSetOption(c2_5, data5_t, data5_v, 2)
					// cSetOption(c2_6, data6_t, data6_v, 2)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 2:
			{
				c3_1 = echarts.init(document.getElementById("c3_1"))
				c3_2 = echarts.init(document.getElementById("c3_2"))

				try {
					let data
					if (!data3) {
						const res = await api.get("/web/statistics/epigenomics")
						data = res.data
						data3 = data
					} else {
						data = data3
					}
					const obj1 = data.histone
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)

					const obj2 = Object.fromEntries(Object.entries(data.top10GenesInDnaMethylation).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					cSetOption(c3_1, data1_t, data1_v)
					cSetOption(c3_2, data2_t, data2_v, 2)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 3:
			{
				c4_1 = echarts.init(document.getElementById("c4_1"))
				// c4_2 = echarts.init(document.getElementById("c4_2"))

				try {
					let data
					if (!data4) {
						const res = await api.get("/web/statistics/metabolomics")
						data = res.data
						data4 = data
					} else {
						data = data4
					}
					const obj1 = data.metabolite
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)

					const obj2 = Object.fromEntries(Object.entries(data.metabolite).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)

					const { t, v } = changeData(data1_t, data1_v)

					cSetOption(c4_1, t, v)

					// cSetOption(c4_2, data2_t, data2_v, 2)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 4:
			{
				c5_1 = echarts.init(document.getElementById("c5_1"))
				// c5_2 = echarts.init(document.getElementById("c5_2"))
				try {
					let data
					if (!data5) {
						const res = await api.get("/web/statistics/proteomics")
						data = res.data
						data5 = data
					} else {
						data = data5
					}
					const obj1 = data.protein
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)
					const obj2 = Object.fromEntries(Object.entries(data.protein).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)

					const { t, v } = changeData(data1_t, data1_v)

					cSetOption(c5_1, t, v)
					// cSetOption(c5_2, data2_t, data2_v, 3)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 5:
			{
				c6_1 = echarts.init(document.getElementById("c6_1"))
				// c6_2 = echarts.init(document.getElementById("c6_2"))
				c6_3 = echarts.init(document.getElementById("c6_3"))
				// c6_4 = echarts.init(document.getElementById("c6_4"))

				try {
					let data
					if (!data6) {
						const res = await api.get("/web/statistics/microbiome")
						data = res.data
						data6 = data
					} else {
						data = data6
					}
					const obj1 = data.generaAssociatedWithIntestinalDiseases
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)
					const obj2 = Object.fromEntries(Object.entries(data.generaAssociatedWithIntestinalDiseases).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					const obj3 = data.speciesAssociatedWithIntestinalDiseases
					const data3_t = Object.keys(obj3)
					const data3_v = Object.values(obj3)
					const obj4 = Object.fromEntries(Object.entries(data.speciesAssociatedWithIntestinalDiseases).sort(([, val1], [, val2]) => val1 - val2))
					const data4_t = Object.keys(obj4)
					const data4_v = Object.values(obj4)

					const { t, v } = changeData(data1_t, data1_v)
					cSetOption(c6_1, t, v)
					// cSetOption(c6_1, data1_t, data1_v)

					// cSetOption(c6_2, data2_t, data2_v, 3)

					const { t: t2, v: v2 } = changeData(data3_t, data3_v)
					cSetOption(c6_3, t2, v2)
					// cSetOption(c6_3, data3_t, data3_v)

					// cSetOption(c6_4, data4_t, data4_v, 3)
					loading.close()
				} catch (error) {
					console.error(error);
					loading.close()
				}
			}
			break
		case 6:
			{
				c7_1 = echarts.init(document.getElementById("c7_1"))
				c7_2 = echarts.init(document.getElementById("c7_2"))
				c7_3 = echarts.init(document.getElementById("c7_3"))
				c7_4 = echarts.init(document.getElementById("c7_4"))
				c7_5 = echarts.init(document.getElementById("c7_5"))
				// c7_6 = echarts.init(document.getElementById("c7_6"))

				try {
					let data
					if (!data7) {
						const res = await api.get("/web/statistics/therapy")
						data = res.data
						data7 = data
					} else {
						data = data7
					}
					const obj1 = data.chemicalCompounds
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)
					const obj2 = Object.fromEntries(Object.entries(data.top10ChemicalCompoundsAssociatedWithIntestinalDiseases).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					const obj3 = data.chineseMedicine
					const data3_t = Object.keys(obj3)
					const data3_v = Object.values(obj3)
					const obj4 = Object.fromEntries(Object.entries(data.top10ChineseMedicineAssociatedWithIntestinalDiseases).sort(([, val1], [, val2]) => val1 - val2))
					const data4_t = Object.keys(obj4)
					const data4_v = Object.values(obj4)
					const obj5 = data.probiotics
					const data5_t = Object.keys(obj5)
					const data5_v = Object.values(obj5)
					const obj6 = Object.fromEntries(Object.entries(data.top10ProbioticsAssociatedWithIntestinalDiseases).sort(([, val1], [, val2]) => val1 - val2))
					const data6_t = Object.keys(obj6)
					const data6_v = Object.values(obj6)
					const { t, v } = changeData(data1_t, data1_v)

					cSetOption(c7_1, t, v)
					cSetOption(c7_2, data2_t, data2_v, 3)

					const { t: t3, v: v3 } = changeData(data3_t, data3_v)
					cSetOption(c7_3, t3, v3)
					cSetOption(c7_4, data4_t, data4_v, 3)
					const { t: t5, v: v5 } = changeData(data5_t, data5_v)
					cSetOption(c7_5, t5, v5)
					// cSetOption(c7_6, data6_t, data6_v, 3)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 7:
			{
				c8_1 = echarts.init(document.getElementById("c8_1"))
				c8_2 = echarts.init(document.getElementById("c8_2"))
				c8_3 = echarts.init(document.getElementById("c8_3"))
				c8_4 = echarts.init(document.getElementById("c8_4"))
				try {
					let data
					if (!data8) {
						const res = await api.get("/web/statistics/transcriptomic")
						data = res.data
						data8 = data
					} else {
						data = data8
					}
					const obj1 = data.nonCodingRna
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)
					const obj2 = Object.fromEntries(Object.entries(data.top10CircRna).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					const obj3 = Object.fromEntries(Object.entries(data.top10LncRna).sort(([, val1], [, val2]) => val1 - val2))
					const data3_t = Object.keys(obj3)
					const data3_v = Object.values(obj3)
					const obj4 = Object.fromEntries(Object.entries(data.top10MiRna).sort(([, val1], [, val2]) => val1 - val2))
					const data4_t = Object.keys(obj4)
					const data4_v = Object.values(obj4)
					cSetOption(c8_1, data1_t, data1_v)
					cSetOption(c8_2, data2_t, data2_v, 3)
					cSetOption(c8_3, data3_t, data3_v, 2)
					cSetOption(c8_4, data4_t, data4_v, 3)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
		case 8:
			{
				c9_1 = echarts.init(document.getElementById("c9_1"))
				c9_2 = echarts.init(document.getElementById("c9_2"))
				try {
					let data
					if (!data9) {
						const res = await api.get("/web/statistics/singleCellOmics")
						data = res.data
						data9 = data
					} else {
						data = data9
					}
					const obj1 = data.cluster
					const data1_t = Object.keys(obj1)
					const data1_v = Object.values(obj1)
					const obj2 = Object.fromEntries(Object.entries(data.top10GenesForSingleCellAlternativeSplicing).sort(([, val1], [, val2]) => val1 - val2))
					const data2_t = Object.keys(obj2)
					const data2_v = Object.values(obj2)
					cSetOption(c9_1, data1_t, data1_v)
					cSetOption(c9_2, data2_t, data2_v, 2)
					loading.close()
				} catch (error) {
					loading.close()
				}
			}
			break
	}
}
</script>

<template>
	<div class="wrap">
		<div class="headers">
			<LayoutNav></LayoutNav>
			<LayoutHeader />
		</div>
		<div class="content_wrap">
			<div class="left">
				<div v-for="(d, i) in arr" class="menu" :class="{ select: active == i }" @click="changeMenu(i)">{{ d }}</div>
			</div>
			<div class="right">
				<Title :title="arr[active]" />
				<!-- <div class="title">{{ arr[active] }}</div> -->
				<div class="contentCharts" v-if="active == 0">
					<div class="df fdc">
						<span class="subTitle">Intestinal Diseases</span>
						<div id="c1_1"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Position</span>
						<div id="c1_2"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Species</span>
						<div id="c1_3"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Genes</span>
						<div id="c1_4"></div>
					</div>
				</div>
				<div class="contentCharts" v-else-if="active == 1">
					<div class="df fdc">
						<span class="subTitle">Genomic Alteration</span>
						<div id="c2_1"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 CNA Genes</span>
						<div id="c2_2"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Mutated Genes</span>
						<div id="c2_3"></div>
					</div> -->
					<div class="df fdc">
						<span class="subTitle">Top 10 SNP</span>
						<div id="c2_4"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 Structural Variant Genes</span>
						<div id="c2_5"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Virulence Genes</span>
						<div id="c2_6"></div>
					</div> -->
				</div>
				<div class="contentCharts" v-else-if="active == 2">
					<div class="df fdc">
						<span class="subTitle">Histone</span>
						<div id="c3_1"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Genes in DNA Methylation</span>
						<div id="c3_2"></div>
					</div>
				</div>
				<div class="contentCharts" v-else-if="active == 3">
					<div class="df fdc">
						<span class="subTitle">Metabolite</span>
						<div id="c4_1"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 Metabolomics associated with Genes</span>
						<div id="c4_2"></div>
					</div> -->
				</div>
				<div class="contentCharts" v-else-if="active == 4">
					<div class="df fdc">
						<span class="subTitle">Protein</span>
						<div id="c5_1"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 Genes associated with Proteomics</span>
						<div id="c5_2"></div>
					</div> -->
				</div>
				<div class="contentCharts" v-else-if="active == 5">
					<div class="df fdc">
						<span class="subTitle">Genera associated with Intestinal Diseases</span>
						<div id="c6_1"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 Genera associated with Intestinal Diseases</span>
						<div id="c6_2"></div>
					</div> -->
					<div class="df fdc">
						<span class="subTitle">Species associated with Intestinal Diseases</span>
						<div id="c6_3"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 Species associated with Intestinal Diseases</span>
						<div id="c6_4"></div>
					</div> -->
				</div>
				<div class="contentCharts" v-else-if="active == 6">
					<div class="df fdc">
						<span class="subTitle">Chemical Compounds</span>
						<div id="c7_1"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Chemical Compounds associated with Intestinal Diseases</span>
						<div id="c7_2"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Traditional Medicine</span>
						<div id="c7_3"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Traditional Medicine associated with Intestinal Diseases</span>
						<div id="c7_4"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Probiotics</span>
						<div id="c7_5"></div>
					</div>
					<!-- <div class="df fdc">
						<span class="subTitle">Top 10 Probiotics associated with Intestinal Diseases</span>
						<div id="c7_6"></div>
					</div> -->
				</div>
				<div class="contentCharts" v-else-if="active == 7">
					<div class="df fdc">
						<span class="subTitle">Non-coding RNA</span>
						<div id="c8_1"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 circRNA</span>
						<div id="c8_2"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 lnc RNA</span>
						<div id="c8_3"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 miRNA</span>
						<div id="c8_4"></div>
					</div>
				</div>
				<div class="contentCharts" v-else-if="active == 8">
					<div class="df fdc">
						<span class="subTitle">Cluster</span>
						<div id="c9_1"></div>
					</div>
					<div class="df fdc">
						<span class="subTitle">Top 10 Genes for Single-cell Alternative Splicing</span>
						<div id="c9_2"></div>
					</div>
				</div>
			</div>
		</div>
		<LayoutFooter></LayoutFooter>
	</div>
</template>

<style lang="scss" scoped>
.wrap {
	.headers {
		display: flex;
		flex-direction: column;
		.header_img {
			object-fit: cover;
			height: 200px;
		}
	}
	.content_wrap {
		display: flex;
		padding: 0 40px 40px;
		position: relative;
		.left {
			display: flex;
			flex-direction: column;
			background-color: white;
			padding: 20px;
			align-self: flex-start;
			.menu {
				cursor: pointer;
				margin-bottom: 20px;
				width: 150px;
				height: 26px;
				border-radius: 5px;
				padding-left: 15px;
				display: flex;
				align-items: center;
				position: relative;
			}
			.menu::after {
				content: "";
				position: absolute;
				left: -4px;
				width: 10px;
				height: 10px;
				border-radius: 50%;
				background-color: #ccc;
			}
			.menu.select {
				background-color: rgba(3, 147, 174, 0.109803921568627);
				color: $themeColor;
			}
			.menu.select::after {
				background-color: rgba(3, 147, 174, 1);
			}
		}
		.right {
			// height: 700px;
			margin-left: 20px;
			display: flex;
			flex: 1;
			flex-direction: column;
			background-color: white;
			overflow-y: auto;
			padding: 20px;
			display: flex;
			flex-direction: column;
			.title {
				display: flex;
				align-items: center;
				padding-left: 15px;
				position: relative;
				height: 26px;
				color: $themeColor;
			}
			.title::after {
				content: "";
				position: absolute;
				left: -4px;
				width: 4px;
				height: 26px;
				background-color: rgba(3, 147, 174, 1);
			}
			.contentCharts {
				padding: 40px 10px 10px;
				font-size: $fontSize14;
				line-height: 20px;
				display: grid;
				// grid-template-columns: 1fr 1fr;
				// grid-template-columns: 1fr;
				gap: 80px;
				justify-items: center;
				div {
					width: 70%;
					height: 400px;
					// position: relative;
				}
				.df.fdc{
					align-items: center;
				}
				.subTitle {
					display: flex;
					justify-content: center;
					align-items: center;
					font-size: $fontSize20;
					margin-bottom: 10px;
					font-weight: bold;
					// position: absolute;
					// top: 0;
					// left: 50%;
					// transform: translate(-50%, -50%);
				}
			}
		}
	}
}
@import url("@/styles/content.scss");
</style>
