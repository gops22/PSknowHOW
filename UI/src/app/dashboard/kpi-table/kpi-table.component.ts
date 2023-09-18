import { Component, Input, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-kpi-table',
  templateUrl: './kpi-table.component.html',
  styleUrls: ['./kpi-table.component.css']
})
export class KpiTableComponent implements OnInit {
  @Input() cols: Array<object> = [];
  @Input() kpiData: object = {};
  @Input() colorObj: object = {};
  @Input() kpiConfigData: object = {};
  activeIndex: number = 0;
  tabs: Array<string> = [];
  showToolTip: boolean = false;
  toolTipHtml: string = '';
  left: string = '';
  top: string = '';
  nodeColors: object = {};
  loader: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.assignColorToNodes();
  }

  ngOnChanges(changes: SimpleChanges) {
    
    let proj = Object.keys(changes['kpiData']?.currentValue)?.[0];
    let condition = changes['kpiData']?.currentValue[proj]?.length > 0;
    
    if (changes['kpiData']?.currentValue != undefined && changes['kpiData']?.currentValue != changes['kpiData']?.previousValue) {
      this.kpiData = changes['kpiData']?.currentValue;
      if(condition){
        this.handleLoader();
      }
    }
    if (changes['colorObj']?.currentValue != changes['colorObj']?.previousValue) {
      this.assignColorToNodes();
    }
    if (changes['kpiConfigData']?.currentValue != changes['kpiConfigData']?.previousValue) {
      this.kpiConfigData = changes['kpiConfigData']?.currentValue;
      this.loader = false;
    }
  }

  handleLoader() {
    this.loader = true;
    let kpisCount = 0;
    Object.values(this.kpiConfigData)?.map(x => {
      if (x == true) {
        kpisCount++;
      }
    });
    let projectWiseLoader = {};
    for (let key in this.kpiData) {
      projectWiseLoader[key] = true;
      if (this.kpiData[key]?.length == kpisCount) {
        projectWiseLoader[key] = false;
      }
    }
    let kpiLoaderValues = Object.values(projectWiseLoader);
    if (!kpiLoaderValues.includes(true)) {
      this.loader = false;
    } else {
      this.loader = true;
    }
  }

  mouseEnter(event, field, data) {
    if (field == 'frequency') {
      if (data?.hoverText?.length > 0) {
        data.hoverText.forEach((item) => {
          this.toolTipHtml += `<span>${item}</span><br/>`;
        });
        this.top = event.pageY + 'px';
        this.left = event.pageX + 'px';
        this.showToolTip = true;
      }
    }
  }

  mouseLeave() {
    this.showToolTip = false;
    this.toolTipHtml = '';
  }

  assignColorToNodes() {
    this.nodeColors = {};
    for (let key in this.colorObj) {
      this.nodeColors[this.colorObj[key]?.nodeName] = this.colorObj[key]?.color;
      this.tabs = Object.keys(this.nodeColors);
    }
  }
}
