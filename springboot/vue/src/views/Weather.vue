<template>
  <div style="margin-left: 10px">
    <el-card  style="width:1260px; height: 320px; margin-top: 6px;padding: 0px" >
      <div>
        <el-table :data="tableData" height="250" border style="width: 100%">
          <el-table-column prop="sign" width="180"></el-table-column>
          <el-table-column prop="hdate" label="日期" width="180"></el-table-column>
          <el-table-column prop="wea" label="天气" width="180"></el-table-column>
          <el-table-column prop="tem" label="气温" width="180"></el-table-column>
          <el-table-column prop="wind" label="风况" width="180"></el-table-column>
          <el-table-column prop="qua" label="空气质量"></el-table-column>
        </el-table>
      </div>
    </el-card>

    <el-row>
      <el-col :span="12">
        <el-popover
          placement="top"
          width="350"
          trigger="hover"
          prop = "Better"
          :content="contentBetter">
          <el-card id="ring" slot="reference"  style="width:632px; height: 500px; margin-top: 20px" ></el-card>
        </el-popover>
      </el-col>

      <el-col :span="12">
        <el-popover
            placement="top"
            width="350"
            trigger="hover"
            content="温度变化，注意更换衣物，疫情防控，出门记得戴口罩呦！！！">
          <el-card id="line" slot="reference"  style="width:632px; height: 500px; margin-top: 20px" ></el-card>
        </el-popover>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  data () {
    return {
      contentBetter:'',
      tableData:null,
      sign:'',
      hdate:'',
      wea:'',
      tem:'',
      wind:'',
      qua:'',
      move:'',
      goOut:'',
      openWin:''

    }
  },

  created() {
    this.load()
    this.better()
  },

  mounted() {

    var ringOption = {
      title: {
        text: '今日空气质量'
      },
      series: [
        {
          name: '今天空气质量',
          type: 'gauge',
          center: ['50%', '75%'],
          startAngle: 180,
          endAngle: 0,
          min: 0,
          max: 500,
          splitNumber: 5,
          itemStyle: {
            color: '#38ef7d'
          },
          progress: {
            show: true,
            width: 30
          },
          pointer: {
            show: false
          },
          axisLine: {
            lineStyle: {
              width: 30
            }
          },
          axisTick: { //轴刻度
            show:false,
            distance: -50,
            splitNumber: 5, //拆分号码
            lineStyle: {
              width: 2,
              color: '#999'
            }
          },
          splitLine: { //分割线
            distance: -45,
            length: 14,
            lineStyle: {
              width: 3,
              color: '#999'
            }
          },
          axisLabel: {
            distance: -20,
            color: '#999',
            fontSize: 20
          },
          anchor: {
            show: false
          },
          title: {

          },
          detail: {
            valueAnimation: true,
            width: '60%',
            lineHeight: 40,
            borderRadius: 8,
            offsetCenter: [0, '-15%'],
            fontSize: 60,
            fontWeight: 'bolder',
            formatter: '{value}',
            color: 'auto'
          },
          data: []
        }
      ]
    };
    var ringDom = document.getElementById('ring');
    var myRing = echarts.init(ringDom);
    this.request.get("/qua").then(res => {
      ringOption.series[0].data = [
        {name:"空气质量指数",value:res[0].quaIndex}
      ]
      myRing.setOption(ringOption);
    })

    //周气温变化
    var lineOption = {
      title: {
        text: '一周气温变化'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {},
      toolbox: {
        show: true,
        feature: {
          dataZoom: {
            yAxisIndex: 'none'
          },
          dataView: { readOnly: false },
          magicType: { type: ['line', 'bar'] },
          restore: {},
          saveAsImage: {}
        }
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: []
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          formatter: '{value} °C'
        }
      },
      series: [
        {
          name: '最高气温变化',
          type: 'line',
          data: [],
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          markLine: {
            data: [{ type: 'average', name: 'Avg' }]
          }
        },
        {
          name: '最低气温变化',
          type: 'line',
          data: [],
          markPoint: {
            data: [{ name: '周最低', value: -2, xAxis: 1, yAxis: -1.5 }]
          },
          markLine: {
            data: [
              { type: 'average', name: 'Avg' },
              [
                {
                  symbol: 'none',
                  x: '90%',
                  yAxis: 'max'
                },
                {
                  symbol: 'circle',
                  label: {
                    position: 'start',
                    formatter: 'Max'
                  },
                  type: 'max',
                  name: '最高点'
                }
              ]
            ]
          }
        }
      ]
    };
    var lineDom = document.getElementById('line');
    var myline = echarts.init(lineDom);

    this.request.get("/weather").then(res => {
      lineOption.xAxis.data = [res[0].sign,res[1].sign,res[2].sign,res[3].sign,res[4].sign,res[5].sign,res[6].sign,res[7].sign]
      lineOption.series[0].data = [res[0].max,res[1].max,res[2].max,res[3].max,res[4].max,res[5].max,res[6].max,res[7].max]
      lineOption.series[1].data = [res[0].min,res[1].min,res[2].min,res[3].min,res[4].min,res[5].min,res[6].min,res[7].min]

      myline.setOption(lineOption);
    })



  },

  methods: {
    load() { //请求数据：包括 页码，每页条数，总条数 用这些数据渲染到页面
      this.request.get("/weather").then(res => {
        console.log(res)
        this.tableData = res
      })
    },
    better(){
      this.request.get("/better").then(res=>{
        this.contentBetter = (res[0].move +'  ,  '+ res[0].goOut +'  ,  '+ res[0].openWin)
      })
    }
  },
}
</script>

<style scoped>

</style>