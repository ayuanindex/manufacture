package com.lenovo.manufacture.Bean;

import java.util.List;

public class MaterialBean {

    /**
     * status : 200
     * message : 获取原材料详情成功
     * data : [{"id":26,"materialName":"MPV方向盘","content":"新星汽车配件供货商","size":1,"icon":"UI_fangxiangpan02","lastUpdateTime":1568009900,"supplyId":1,"materialId":26,"price":1600,"num":2,"costTime":3,"supplyName":"新星汽车配件","supplyListId":1},{"id":22,"materialName":"SUV悬挂","content":"新星汽车配件供货商","size":1,"icon":"UI_xuangua03","lastUpdateTime":1568009900,"supplyId":1,"materialId":22,"price":300,"num":1,"costTime":3,"supplyName":"新星汽车配件","supplyListId":8},{"id":25,"materialName":"轿车方向盘","content":"新星汽车配件供货商","size":1,"icon":"UI_fangxiangpan01","lastUpdateTime":1568009900,"supplyId":1,"materialId":25,"price":1500,"num":1,"costTime":5,"supplyName":"新星汽车配件","supplyListId":3},{"id":24,"materialName":"MPV座椅","content":"新星汽车配件供货商","size":2,"icon":"UI_zhuoyi02","lastUpdateTime":1568009900,"supplyId":1,"materialId":24,"price":1666,"num":1,"costTime":5,"supplyName":"新星汽车配件","supplyListId":4},{"id":23,"materialName":"轿车座椅","content":"新星汽车配件供货商","size":2,"icon":"UI_zhuoyi01","lastUpdateTime":1568009900,"supplyId":1,"materialId":23,"price":1350,"num":1,"costTime":3,"supplyName":"新星汽车配件","supplyListId":5},{"id":21,"materialName":"MPV悬挂","content":"新星汽车配件供货商","size":1,"icon":"UI_xuangua02","lastUpdateTime":1568009900,"supplyId":1,"materialId":21,"price":690,"num":1,"costTime":4,"supplyName":"新星汽车配件","supplyListId":9},{"id":20,"materialName":"轿车悬挂","content":"新星汽车配件供货商","size":1,"icon":"UI_xuangua01","lastUpdateTime":1568009900,"supplyId":1,"materialId":20,"price":4630,"num":10,"costTime":4,"supplyName":"新星汽车配件","supplyListId":10},{"id":19,"materialName":"SUV车架","content":"新星汽车配件供货商","size":2,"icon":"UI_body03","lastUpdateTime":1568009900,"supplyId":1,"materialId":19,"price":26000,"num":3,"costTime":5,"supplyName":"新星汽车配件","supplyListId":11},{"id":18,"materialName":"SUV前盖","content":"新星汽车配件供货商","size":1,"icon":"UI_qiangai03","lastUpdateTime":1568009900,"supplyId":1,"materialId":18,"price":4500,"num":3,"costTime":5,"supplyName":"新星汽车配件","supplyListId":12},{"id":17,"materialName":"MPV前盖","content":"新星汽车配件供货商","size":1,"icon":"UI_qiangai02","lastUpdateTime":1568009900,"supplyId":1,"materialId":17,"price":2555,"num":1,"costTime":5,"supplyName":"新星汽车配件","supplyListId":13},{"id":16,"materialName":"轿车前盖","content":"新星汽车配件供货商","size":1,"icon":"UI_qiangai01","lastUpdateTime":1568009900,"supplyId":1,"materialId":16,"price":1630,"num":1,"costTime":6,"supplyName":"新星汽车配件","supplyListId":14},{"id":15,"materialName":"SUV底盘","content":"新星汽车配件供货商","size":3,"icon":"UI_dipan03","lastUpdateTime":1568009900,"supplyId":1,"materialId":15,"price":200,"num":2,"costTime":6,"supplyName":"新星汽车配件","supplyListId":15},{"id":14,"materialName":"MPV底盘","content":"新星汽车配件供货商","size":3,"icon":"UI_dipan02","lastUpdateTime":1568009900,"supplyId":1,"materialId":14,"price":750,"num":2,"costTime":5,"supplyName":"新星汽车配件","supplyListId":16},{"id":13,"materialName":"轿车底盘","content":"新星汽车配件供货商","size":3,"icon":"UI_dipan01","lastUpdateTime":1568009900,"supplyId":1,"materialId":13,"price":8000,"num":12,"costTime":5,"supplyName":"新星汽车配件","supplyListId":17},{"id":12,"materialName":"SUV引擎","content":"新星汽车配件供货商","size":2,"icon":"UI_fadongji03","lastUpdateTime":1568009900,"supplyId":1,"materialId":12,"price":2600,"num":4,"costTime":4,"supplyName":"新星汽车配件","supplyListId":18},{"id":11,"materialName":"MPV引擎","content":"新星汽车配件供货商","size":2,"icon":"UI_fadongji02","lastUpdateTime":1568009900,"supplyId":1,"materialId":11,"price":600,"num":1,"costTime":2,"supplyName":"新星汽车配件","supplyListId":19},{"id":10,"materialName":"SUV前玻璃","content":"新星汽车配件供货商","size":1,"icon":"UI_qianboli03","lastUpdateTime":1568009900,"supplyId":1,"materialId":10,"price":1500,"num":1,"costTime":5,"supplyName":"新星汽车配件","supplyListId":20},{"id":9,"materialName":"SUV轮胎","content":"新星汽车配件供货商","size":1,"icon":"UI_luntai03","lastUpdateTime":1568009900,"supplyId":1,"materialId":9,"price":1400,"num":1,"costTime":2,"supplyName":"新星汽车配件","supplyListId":21},{"id":7,"materialName":"轿车轮胎","content":"新星汽车配件供货商","size":1,"icon":"UI_luntai01","lastUpdateTime":1568009900,"supplyId":1,"materialId":7,"price":1888,"num":1,"costTime":1,"supplyName":"新星汽车配件","supplyListId":22},{"id":8,"materialName":"MPV轮胎","content":"新星汽车配件供货商","size":1,"icon":"UI_luntai02","lastUpdateTime":1568009900,"supplyId":1,"materialId":8,"price":1666,"num":1,"costTime":1,"supplyName":"新星汽车配件","supplyListId":23},{"id":5,"materialName":"MPV前玻璃","content":"新星汽车配件供货商","size":2,"icon":"UI_qianboli02","lastUpdateTime":1568009900,"supplyId":1,"materialId":5,"price":1666,"num":4,"costTime":1,"supplyName":"新星汽车配件","supplyListId":24},{"id":4,"materialName":"轿车前玻璃","content":"新星汽车配件供货商","size":2,"icon":"UI_qianboli01","lastUpdateTime":1568009900,"supplyId":1,"materialId":4,"price":800,"num":1,"costTime":1,"supplyName":"新星汽车配件","supplyListId":25},{"id":1,"materialName":"轿车引擎","content":"新星汽车配件供货商","size":2,"icon":"UI_fadongji01","lastUpdateTime":1568009900,"supplyId":1,"materialId":1,"price":700,"num":1,"costTime":1,"supplyName":"新星汽车配件","supplyListId":26},{"id":30,"materialName":"MPV车架","content":"新星汽车配件供货商","size":2,"icon":"UI_body02","lastUpdateTime":1568009900,"supplyId":1,"materialId":30,"price":3450,"num":6,"costTime":6,"supplyName":"新星汽车配件","supplyListId":30},{"id":29,"materialName":"轿车车架","content":"新星汽车配件供货商","size":2,"icon":"UI_body01","lastUpdateTime":1568009900,"supplyId":1,"materialId":29,"price":9000,"num":15,"costTime":5,"supplyName":"新星汽车配件","supplyListId":29},{"id":31,"materialName":"轿车车门","content":"新星汽车配件供货商","size":1,"icon":"UI_door01","lastUpdateTime":1568009900,"supplyId":1,"materialId":31,"price":2560,"num":7,"costTime":5,"supplyName":"新星汽车配件","supplyListId":31},{"id":32,"materialName":"MPV车门","content":"新星汽车配件供货商","size":1,"icon":"UI_door02","lastUpdateTime":1568009900,"supplyId":1,"materialId":32,"price":3200,"num":4,"costTime":9,"supplyName":"新星汽车配件","supplyListId":32},{"id":33,"materialName":"SUV车门","content":"新星汽车配件供货商","size":1,"icon":"UI_door03","lastUpdateTime":1568009900,"supplyId":1,"materialId":33,"price":1500,"num":1,"costTime":4,"supplyName":"新星汽车配件","supplyListId":33},{"id":34,"materialName":"SUV方向盘","content":"新星汽车配件供货商","size":1,"icon":"UI_fangxiangpan03","lastUpdateTime":1568009900,"supplyId":1,"materialId":34,"price":1600,"num":3,"costTime":3,"supplyName":"新星汽车配件","supplyListId":34},{"id":35,"materialName":"轿车后玻璃","content":"新星汽车配件供货商","size":1,"icon":"UI_houboli01","lastUpdateTime":1568009900,"supplyId":1,"materialId":35,"price":2810,"num":7,"costTime":1,"supplyName":"新星汽车配件","supplyListId":35},{"id":36,"materialName":"MPV后玻璃","content":"新星汽车配件供货商","size":1,"icon":"UI_houboli02","lastUpdateTime":1568009900,"supplyId":1,"materialId":36,"price":688,"num":1,"costTime":2,"supplyName":"新星汽车配件","supplyListId":36},{"id":37,"materialName":"SUV后玻璃","content":"新星汽车配件供货商","size":1,"icon":"UI_houboli03","lastUpdateTime":1568009900,"supplyId":1,"materialId":37,"price":485,"num":1,"costTime":8,"supplyName":"新星汽车配件","supplyListId":37},{"id":38,"materialName":"SUV座椅","content":"新星汽车配件供货商","size":2,"icon":"UI_zhuoyi03","lastUpdateTime":1568009900,"supplyId":1,"materialId":38,"price":6000,"num":25,"costTime":4,"supplyName":"新星汽车配件","supplyListId":38},{"id":38,"materialName":"SUV座椅","content":"枫叶汽车配件供货商","size":2,"icon":"UI_zhuoyi03","lastUpdateTime":1568009927,"supplyId":2,"materialId":38,"price":1650,"num":3,"costTime":3,"supplyName":"枫叶汽车配件","supplyListId":39},{"id":37,"materialName":"SUV后玻璃","content":"枫叶汽车配件供货商","size":1,"icon":"UI_houboli03","lastUpdateTime":1568009927,"supplyId":2,"materialId":37,"price":1660,"num":9,"costTime":4,"supplyName":"枫叶汽车配件","supplyListId":40},{"id":36,"materialName":"MPV后玻璃","content":"枫叶汽车配件供货商","size":1,"icon":"UI_houboli02","lastUpdateTime":1568009927,"supplyId":2,"materialId":36,"price":2060,"num":1,"costTime":9,"supplyName":"枫叶汽车配件","supplyListId":41},{"id":35,"materialName":"轿车后玻璃","content":"枫叶汽车配件供货商","size":1,"icon":"UI_houboli01","lastUpdateTime":1568009927,"supplyId":2,"materialId":35,"price":2080,"num":3,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":42},{"id":34,"materialName":"SUV方向盘","content":"枫叶汽车配件供货商","size":1,"icon":"UI_fangxiangpan03","lastUpdateTime":1568009927,"supplyId":2,"materialId":34,"price":5700,"num":10,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":43},{"id":33,"materialName":"SUV车门","content":"枫叶汽车配件供货商","size":1,"icon":"UI_door03","lastUpdateTime":1568009927,"supplyId":2,"materialId":33,"price":2600,"num":5,"costTime":7,"supplyName":"枫叶汽车配件","supplyListId":44},{"id":32,"materialName":"MPV车门","content":"枫叶汽车配件供货商","size":1,"icon":"UI_door02","lastUpdateTime":1568009927,"supplyId":2,"materialId":32,"price":6500,"num":10,"costTime":8,"supplyName":"枫叶汽车配件","supplyListId":45},{"id":31,"materialName":"轿车车门","content":"枫叶汽车配件供货商","size":1,"icon":"UI_door01","lastUpdateTime":1568009927,"supplyId":2,"materialId":31,"price":8700,"num":22,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":46},{"id":30,"materialName":"MPV车架","content":"枫叶汽车配件供货商","size":2,"icon":"UI_body02","lastUpdateTime":1568009927,"supplyId":2,"materialId":30,"price":3200,"num":5,"costTime":1,"supplyName":"枫叶汽车配件","supplyListId":47},{"id":29,"materialName":"轿车车架","content":"枫叶汽车配件供货商","size":2,"icon":"UI_body01","lastUpdateTime":1568009927,"supplyId":2,"materialId":29,"price":300,"num":1,"costTime":4,"supplyName":"枫叶汽车配件","supplyListId":48},{"id":26,"materialName":"MPV方向盘","content":"枫叶汽车配件供货商","size":1,"icon":"UI_fangxiangpan02","lastUpdateTime":1568009927,"supplyId":2,"materialId":26,"price":488,"num":1,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":49},{"id":25,"materialName":"轿车方向盘","content":"枫叶汽车配件供货商","size":1,"icon":"UI_fangxiangpan01","lastUpdateTime":1568009927,"supplyId":2,"materialId":25,"price":3666,"num":3,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":50},{"id":24,"materialName":"MPV座椅","content":"枫叶汽车配件供货商","size":2,"icon":"UI_zhuoyi02","lastUpdateTime":1568009927,"supplyId":2,"materialId":24,"price":5000,"num":6,"costTime":7,"supplyName":"枫叶汽车配件","supplyListId":51},{"id":23,"materialName":"轿车座椅","content":"枫叶汽车配件供货商","size":2,"icon":"UI_zhuoyi01","lastUpdateTime":1568009927,"supplyId":2,"materialId":23,"price":9700,"num":10,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":52},{"id":22,"materialName":"SUV悬挂","content":"枫叶汽车配件供货商","size":1,"icon":"UI_xuangua03","lastUpdateTime":1568009927,"supplyId":2,"materialId":22,"price":6600,"num":25,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":53},{"id":21,"materialName":"MPV悬挂","content":"枫叶汽车配件供货商","size":1,"icon":"UI_xuangua02","lastUpdateTime":1568009927,"supplyId":2,"materialId":21,"price":1300,"num":2,"costTime":3,"supplyName":"枫叶汽车配件","supplyListId":54},{"id":20,"materialName":"轿车悬挂","content":"枫叶汽车配件供货商","size":1,"icon":"UI_xuangua01","lastUpdateTime":1568009927,"supplyId":2,"materialId":20,"price":4810,"num":5,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":55},{"id":19,"materialName":"SUV车架","content":"枫叶汽车配件供货商","size":2,"icon":"UI_body03","lastUpdateTime":1568009927,"supplyId":2,"materialId":19,"price":2148,"num":8,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":56},{"id":18,"materialName":"SUV前盖","content":"枫叶汽车配件供货商","size":1,"icon":"UI_qiangai03","lastUpdateTime":1568009927,"supplyId":2,"materialId":18,"price":2669,"num":1,"costTime":2,"supplyName":"枫叶汽车配件","supplyListId":57},{"id":17,"materialName":"MPV前盖","content":"枫叶汽车配件供货商","size":1,"icon":"UI_qiangai02","lastUpdateTime":1568009927,"supplyId":2,"materialId":17,"price":200,"num":1,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":58},{"id":16,"materialName":"轿车前盖","content":"枫叶汽车配件供货商","size":1,"icon":"UI_qiangai01","lastUpdateTime":1568009927,"supplyId":2,"materialId":16,"price":499,"num":1,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":59},{"id":15,"materialName":"SUV底盘","content":"枫叶汽车配件供货商","size":3,"icon":"UI_dipan03","lastUpdateTime":1568009927,"supplyId":2,"materialId":15,"price":1400,"num":1,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":60},{"id":14,"materialName":"MPV底盘","content":"枫叶汽车配件供货商","size":3,"icon":"UI_dipan02","lastUpdateTime":1568009927,"supplyId":2,"materialId":14,"price":1989,"num":3,"costTime":8,"supplyName":"枫叶汽车配件","supplyListId":61},{"id":13,"materialName":"轿车底盘","content":"枫叶汽车配件供货商","size":3,"icon":"UI_dipan01","lastUpdateTime":1568009927,"supplyId":2,"materialId":13,"price":1999,"num":5,"costTime":8,"supplyName":"枫叶汽车配件","supplyListId":62},{"id":12,"materialName":"SUV引擎","content":"枫叶汽车配件供货商","size":2,"icon":"UI_fadongji03","lastUpdateTime":1568009927,"supplyId":2,"materialId":12,"price":3999,"num":8,"costTime":9,"supplyName":"枫叶汽车配件","supplyListId":63},{"id":11,"materialName":"MPV引擎","content":"枫叶汽车配件供货商","size":2,"icon":"UI_fadongji02","lastUpdateTime":1568009927,"supplyId":2,"materialId":11,"price":3666,"num":7,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":64},{"id":10,"materialName":"SUV前玻璃","content":"枫叶汽车配件供货商","size":1,"icon":"UI_qianboli03","lastUpdateTime":1568009927,"supplyId":2,"materialId":10,"price":1550,"num":6,"costTime":9,"supplyName":"枫叶汽车配件","supplyListId":65},{"id":9,"materialName":"SUV轮胎","content":"枫叶汽车配件供货商","size":1,"icon":"UI_luntai03","lastUpdateTime":1568009927,"supplyId":2,"materialId":9,"price":3690,"num":12,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":66},{"id":7,"materialName":"轿车轮胎","content":"枫叶汽车配件供货商","size":1,"icon":"UI_luntai01","lastUpdateTime":1568009927,"supplyId":2,"materialId":7,"price":6400,"num":12,"costTime":4,"supplyName":"枫叶汽车配件","supplyListId":67},{"id":8,"materialName":"MPV轮胎","content":"枫叶汽车配件供货商","size":1,"icon":"UI_luntai02","lastUpdateTime":1568009927,"supplyId":2,"materialId":8,"price":600,"num":1,"costTime":7,"supplyName":"枫叶汽车配件","supplyListId":68},{"id":5,"materialName":"MPV前玻璃","content":"枫叶汽车配件供货商","size":2,"icon":"UI_qianboli02","lastUpdateTime":1568009927,"supplyId":2,"materialId":5,"price":420,"num":2,"costTime":6,"supplyName":"枫叶汽车配件","supplyListId":69},{"id":4,"materialName":"轿车前玻璃","content":"枫叶汽车配件供货商","size":2,"icon":"UI_qianboli01","lastUpdateTime":1568009927,"supplyId":2,"materialId":4,"price":3333,"num":6,"costTime":5,"supplyName":"枫叶汽车配件","supplyListId":70},{"id":1,"materialName":"轿车引擎","content":"枫叶汽车配件供货商","size":2,"icon":"UI_fadongji01","lastUpdateTime":1568009927,"supplyId":2,"materialId":1,"price":1908,"num":2,"costTime":4,"supplyName":"枫叶汽车配件","supplyListId":71},{"id":38,"materialName":"SUV座椅","content":"天辉汽车配件供货商","size":2,"icon":"UI_zhuoyi03","lastUpdateTime":1568009949,"supplyId":5,"materialId":38,"price":1804,"num":5,"costTime":3,"supplyName":"天辉汽车配件","supplyListId":72},{"id":37,"materialName":"SUV后玻璃","content":"天辉汽车配件供货商","size":1,"icon":"UI_houboli03","lastUpdateTime":1568009949,"supplyId":5,"materialId":37,"price":1840,"num":4,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":73},{"id":36,"materialName":"MPV后玻璃","content":"天辉汽车配件供货商","size":1,"icon":"UI_houboli02","lastUpdateTime":1568009949,"supplyId":5,"materialId":36,"price":5500,"num":10,"costTime":4,"supplyName":"天辉汽车配件","supplyListId":74},{"id":35,"materialName":"轿车后玻璃","content":"天辉汽车配件供货商","size":1,"icon":"UI_houboli01","lastUpdateTime":1568009949,"supplyId":5,"materialId":35,"price":15000,"num":50,"costTime":7,"supplyName":"天辉汽车配件","supplyListId":75},{"id":34,"materialName":"SUV方向盘","content":"天辉汽车配件供货商","size":1,"icon":"UI_fangxiangpan03","lastUpdateTime":1568009949,"supplyId":5,"materialId":34,"price":3600,"num":5,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":76},{"id":33,"materialName":"SUV车门","content":"天辉汽车配件供货商","size":1,"icon":"UI_door03","lastUpdateTime":1568009949,"supplyId":5,"materialId":33,"price":5888,"num":11,"costTime":4,"supplyName":"天辉汽车配件","supplyListId":77},{"id":32,"materialName":"MPV车门","content":"天辉汽车配件供货商","size":1,"icon":"UI_door02","lastUpdateTime":1568009949,"supplyId":5,"materialId":32,"price":548,"num":1,"costTime":3,"supplyName":"天辉汽车配件","supplyListId":78},{"id":31,"materialName":"轿车车门","content":"天辉汽车配件供货商","size":1,"icon":"UI_door01","lastUpdateTime":1568009949,"supplyId":5,"materialId":31,"price":5900,"num":11,"costTime":3,"supplyName":"天辉汽车配件","supplyListId":79},{"id":30,"materialName":"MPV车架","content":"天辉汽车配件供货商","size":2,"icon":"UI_body02","lastUpdateTime":1568009949,"supplyId":5,"materialId":30,"price":328,"num":1,"costTime":9,"supplyName":"天辉汽车配件","supplyListId":80},{"id":29,"materialName":"轿车车架","content":"天辉汽车配件供货商","size":2,"icon":"UI_body01","lastUpdateTime":1568009949,"supplyId":5,"materialId":29,"price":3250,"num":11,"costTime":8,"supplyName":"天辉汽车配件","supplyListId":81},{"id":26,"materialName":"MPV方向盘","content":"天辉汽车配件供货商","size":1,"icon":"UI_fangxiangpan02","lastUpdateTime":1568009949,"supplyId":5,"materialId":26,"price":299,"num":1,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":82},{"id":25,"materialName":"轿车方向盘","content":"天辉汽车配件供货商","size":1,"icon":"UI_fangxiangpan01","lastUpdateTime":1568009949,"supplyId":5,"materialId":25,"price":229,"num":1,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":83},{"id":24,"materialName":"MPV座椅","content":"天辉汽车配件供货商","size":2,"icon":"UI_zhuoyi02","lastUpdateTime":1568009949,"supplyId":5,"materialId":24,"price":449,"num":1,"costTime":3,"supplyName":"天辉汽车配件","supplyListId":84},{"id":23,"materialName":"轿车座椅","content":"天辉汽车配件供货商","size":2,"icon":"UI_zhuoyi01","lastUpdateTime":1568009949,"supplyId":5,"materialId":23,"price":960,"num":3,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":85},{"id":22,"materialName":"SUV悬挂","content":"天辉汽车配件供货商","size":1,"icon":"UI_xuangua03","lastUpdateTime":1568009949,"supplyId":5,"materialId":22,"price":7800,"num":12,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":86},{"id":21,"materialName":"MPV悬挂","content":"天辉汽车配件供货商","size":1,"icon":"UI_xuangua02","lastUpdateTime":1568009949,"supplyId":5,"materialId":21,"price":690,"num":1,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":87},{"id":20,"materialName":"轿车悬挂","content":"天辉汽车配件供货商","size":1,"icon":"UI_xuangua01","lastUpdateTime":1568009949,"supplyId":5,"materialId":20,"price":800,"num":2,"costTime":5,"supplyName":"天辉汽车配件","supplyListId":88},{"id":19,"materialName":"SUV车架","content":"天辉汽车配件供货商","size":2,"icon":"UI_body03","lastUpdateTime":1568009949,"supplyId":5,"materialId":19,"price":4900,"num":2,"costTime":4,"supplyName":"天辉汽车配件","supplyListId":89},{"id":18,"materialName":"SUV前盖","content":"天辉汽车配件供货商","size":1,"icon":"UI_qiangai03","lastUpdateTime":1568009949,"supplyId":5,"materialId":18,"price":3600,"num":2,"costTime":5,"supplyName":"天辉汽车配件","supplyListId":90},{"id":17,"materialName":"MPV前盖","content":"天辉汽车配件供货商","size":1,"icon":"UI_qiangai02","lastUpdateTime":1568009949,"supplyId":5,"materialId":17,"price":3450,"num":5,"costTime":8,"supplyName":"天辉汽车配件","supplyListId":91},{"id":16,"materialName":"轿车前盖","content":"天辉汽车配件供货商","size":1,"icon":"UI_qiangai01","lastUpdateTime":1568009949,"supplyId":5,"materialId":16,"price":16000,"num":25,"costTime":4,"supplyName":"天辉汽车配件","supplyListId":92},{"id":15,"materialName":"SUV底盘","content":"天辉汽车配件供货商","size":3,"icon":"UI_dipan03","lastUpdateTime":1568009949,"supplyId":5,"materialId":15,"price":4800,"num":6,"costTime":7,"supplyName":"天辉汽车配件","supplyListId":93},{"id":14,"materialName":"MPV底盘","content":"天辉汽车配件供货商","size":3,"icon":"UI_dipan02","lastUpdateTime":1568009949,"supplyId":5,"materialId":14,"price":7510,"num":12,"costTime":8,"supplyName":"天辉汽车配件","supplyListId":94},{"id":13,"materialName":"轿车底盘","content":"天辉汽车配件供货商","size":3,"icon":"UI_dipan01","lastUpdateTime":1568009949,"supplyId":5,"materialId":13,"price":2600,"num":3,"costTime":2,"supplyName":"天辉汽车配件","supplyListId":95},{"id":12,"materialName":"SUV引擎","content":"天辉汽车配件供货商","size":2,"icon":"UI_fadongji03","lastUpdateTime":1568009949,"supplyId":5,"materialId":12,"price":6500,"num":7,"costTime":2,"supplyName":"天辉汽车配件","supplyListId":96},{"id":11,"materialName":"MPV引擎","content":"天辉汽车配件供货商","size":2,"icon":"UI_fadongji02","lastUpdateTime":1568009949,"supplyId":5,"materialId":11,"price":3888,"num":5,"costTime":2,"supplyName":"天辉汽车配件","supplyListId":97},{"id":10,"materialName":"SUV前玻璃","content":"天辉汽车配件供货商","size":1,"icon":"UI_qianboli03","lastUpdateTime":1568009949,"supplyId":5,"materialId":10,"price":480,"num":1,"costTime":6,"supplyName":"天辉汽车配件","supplyListId":98},{"id":9,"materialName":"SUV轮胎","content":"天辉汽车配件供货商","size":1,"icon":"UI_luntai03","lastUpdateTime":1568009949,"supplyId":5,"materialId":9,"price":960,"num":1,"costTime":4,"supplyName":"天辉汽车配件","supplyListId":99},{"id":7,"materialName":"轿车轮胎","content":"天辉汽车配件供货商","size":1,"icon":"UI_luntai01","lastUpdateTime":1568009949,"supplyId":5,"materialId":7,"price":9600,"num":12,"costTime":5,"supplyName":"天辉汽车配件","supplyListId":100},{"id":8,"materialName":"MPV轮胎","content":"天辉汽车配件供货商","size":1,"icon":"UI_luntai02","lastUpdateTime":1568009949,"supplyId":5,"materialId":8,"price":460,"num":1,"costTime":5,"supplyName":"天辉汽车配件","supplyListId":101},{"id":5,"materialName":"MPV前玻璃","content":"天辉汽车配件供货商","size":2,"icon":"UI_qianboli02","lastUpdateTime":1568009949,"supplyId":5,"materialId":5,"price":5300,"num":12,"costTime":7,"supplyName":"天辉汽车配件","supplyListId":102},{"id":4,"materialName":"轿车前玻璃","content":"天辉汽车配件供货商","size":2,"icon":"UI_qianboli01","lastUpdateTime":1568009949,"supplyId":5,"materialId":4,"price":6000,"num":5,"costTime":9,"supplyName":"天辉汽车配件","supplyListId":103},{"id":1,"materialName":"轿车引擎","content":"天辉汽车配件供货商","size":2,"icon":"UI_fadongji01","lastUpdateTime":1568009949,"supplyId":5,"materialId":1,"price":2500,"num":5,"costTime":7,"supplyName":"天辉汽车配件","supplyListId":104},{"id":38,"materialName":"SUV座椅","content":"细末汽车配件供货商","size":2,"icon":"UI_zhuoyi03","lastUpdateTime":1568009993,"supplyId":6,"materialId":38,"price":3600,"num":6,"costTime":1,"supplyName":"细末汽车配件","supplyListId":105},{"id":37,"materialName":"SUV后玻璃","content":"细末汽车配件供货商","size":1,"icon":"UI_houboli03","lastUpdateTime":1568009993,"supplyId":6,"materialId":37,"price":2850,"num":9,"costTime":5,"supplyName":"细末汽车配件","supplyListId":106},{"id":36,"materialName":"MPV后玻璃","content":"细末汽车配件供货商","size":1,"icon":"UI_houboli02","lastUpdateTime":1568009993,"supplyId":6,"materialId":36,"price":2666,"num":5,"costTime":3,"supplyName":"细末汽车配件","supplyListId":107},{"id":35,"materialName":"轿车后玻璃","content":"细末汽车配件供货商","size":1,"icon":"UI_houboli01","lastUpdateTime":1568009993,"supplyId":6,"materialId":35,"price":16000,"num":30,"costTime":4,"supplyName":"细末汽车配件","supplyListId":108},{"id":34,"materialName":"SUV方向盘","content":"细末汽车配件供货商","size":1,"icon":"UI_fangxiangpan03","lastUpdateTime":1568009993,"supplyId":6,"materialId":34,"price":4325,"num":10,"costTime":8,"supplyName":"细末汽车配件","supplyListId":109},{"id":33,"materialName":"SUV车门","content":"细末汽车配件供货商","size":1,"icon":"UI_door03","lastUpdateTime":1568009993,"supplyId":6,"materialId":33,"price":960,"num":1,"costTime":2,"supplyName":"细末汽车配件","supplyListId":110},{"id":32,"materialName":"MPV车门","content":"细末汽车配件供货商","size":1,"icon":"UI_door02","lastUpdateTime":1568009993,"supplyId":6,"materialId":32,"price":6700,"num":10,"costTime":1,"supplyName":"细末汽车配件","supplyListId":111},{"id":31,"materialName":"轿车车门","content":"细末汽车配件供货商","size":1,"icon":"UI_door01","lastUpdateTime":1568009993,"supplyId":6,"materialId":31,"price":3600,"num":6,"costTime":2,"supplyName":"细末汽车配件","supplyListId":112},{"id":30,"materialName":"MPV车架","content":"细末汽车配件供货商","size":2,"icon":"UI_body02","lastUpdateTime":1568009993,"supplyId":6,"materialId":30,"price":1555,"num":5,"costTime":3,"supplyName":"细末汽车配件","supplyListId":113},{"id":29,"materialName":"轿车车架","content":"细末汽车配件供货商","size":2,"icon":"UI_body01","lastUpdateTime":1568009993,"supplyId":6,"materialId":29,"price":350,"num":1,"costTime":3,"supplyName":"细末汽车配件","supplyListId":114},{"id":26,"materialName":"MPV方向盘","content":"细末汽车配件供货商","size":1,"icon":"UI_fangxiangpan02","lastUpdateTime":1568009993,"supplyId":6,"materialId":26,"price":2800,"num":10,"costTime":4,"supplyName":"细末汽车配件","supplyListId":115},{"id":25,"materialName":"轿车方向盘","content":"细末汽车配件供货商","size":1,"icon":"UI_fangxiangpan01","lastUpdateTime":1568009993,"supplyId":6,"materialId":25,"price":300,"num":1,"costTime":5,"supplyName":"细末汽车配件","supplyListId":116},{"id":24,"materialName":"MPV座椅","content":"细末汽车配件供货商","size":2,"icon":"UI_zhuoyi02","lastUpdateTime":1568009993,"supplyId":6,"materialId":24,"price":4555,"num":5,"costTime":5,"supplyName":"细末汽车配件","supplyListId":117},{"id":23,"materialName":"轿车座椅","content":"细末汽车配件供货商","size":2,"icon":"UI_zhuoyi01","lastUpdateTime":1568009993,"supplyId":6,"materialId":23,"price":960,"num":3,"costTime":6,"supplyName":"细末汽车配件","supplyListId":118},{"id":22,"materialName":"SUV悬挂","content":"细末汽车配件供货商","size":1,"icon":"UI_xuangua03","lastUpdateTime":1568009993,"supplyId":6,"materialId":22,"price":180,"num":1,"costTime":7,"supplyName":"细末汽车配件","supplyListId":119},{"id":21,"materialName":"MPV悬挂","content":"细末汽车配件供货商","size":1,"icon":"UI_xuangua02","lastUpdateTime":1568009993,"supplyId":6,"materialId":21,"price":1080,"num":5,"costTime":8,"supplyName":"细末汽车配件","supplyListId":120},{"id":20,"materialName":"轿车悬挂","content":"细末汽车配件供货商","size":1,"icon":"UI_xuangua01","lastUpdateTime":1568009993,"supplyId":6,"materialId":20,"price":300,"num":1,"costTime":9,"supplyName":"细末汽车配件","supplyListId":121},{"id":19,"materialName":"SUV车架","content":"细末汽车配件供货商","size":2,"icon":"UI_body03","lastUpdateTime":1568009993,"supplyId":6,"materialId":19,"price":160,"num":1,"costTime":9,"supplyName":"细末汽车配件","supplyListId":122},{"id":18,"materialName":"SUV前盖","content":"细末汽车配件供货商","size":1,"icon":"UI_qiangai03","lastUpdateTime":1568009993,"supplyId":6,"materialId":18,"price":860,"num":2,"costTime":8,"supplyName":"细末汽车配件","supplyListId":123},{"id":17,"materialName":"MPV前盖","content":"细末汽车配件供货商","size":1,"icon":"UI_qiangai02","lastUpdateTime":1568009993,"supplyId":6,"materialId":17,"price":1060,"num":2,"costTime":8,"supplyName":"细末汽车配件","supplyListId":124},{"id":16,"materialName":"轿车前盖","content":"细末汽车配件供货商","size":1,"icon":"UI_qiangai01","lastUpdateTime":1568009993,"supplyId":6,"materialId":16,"price":677,"num":1,"costTime":7,"supplyName":"细末汽车配件","supplyListId":125},{"id":15,"materialName":"SUV底盘","content":"细末汽车配件供货商","size":3,"icon":"UI_dipan03","lastUpdateTime":1568009993,"supplyId":6,"materialId":15,"price":3487,"num":8,"costTime":6,"supplyName":"细末汽车配件","supplyListId":126},{"id":14,"materialName":"MPV底盘","content":"细末汽车配件供货商","size":3,"icon":"UI_dipan02","lastUpdateTime":1568009993,"supplyId":6,"materialId":14,"price":1265,"num":2,"costTime":5,"supplyName":"细末汽车配件","supplyListId":127},{"id":13,"materialName":"轿车底盘","content":"细末汽车配件供货商","size":3,"icon":"UI_dipan01","lastUpdateTime":1568009993,"supplyId":6,"materialId":13,"price":1854,"num":1,"costTime":4,"supplyName":"细末汽车配件","supplyListId":128},{"id":12,"materialName":"SUV引擎","content":"细末汽车配件供货商","size":2,"icon":"UI_fadongji03","lastUpdateTime":1568009993,"supplyId":6,"materialId":12,"price":4698,"num":2,"costTime":4,"supplyName":"细末汽车配件","supplyListId":129},{"id":11,"materialName":"MPV引擎","content":"细末汽车配件供货商","size":2,"icon":"UI_fadongji02","lastUpdateTime":1568009993,"supplyId":6,"materialId":11,"price":1489,"num":10,"costTime":3,"supplyName":"细末汽车配件","supplyListId":130},{"id":10,"materialName":"SUV前玻璃","content":"细末汽车配件供货商","size":1,"icon":"UI_qianboli03","lastUpdateTime":1568009993,"supplyId":6,"materialId":10,"price":4891,"num":7,"costTime":2,"supplyName":"细末汽车配件","supplyListId":131},{"id":9,"materialName":"SUV轮胎","content":"细末汽车配件供货商","size":1,"icon":"UI_luntai03","lastUpdateTime":1568009993,"supplyId":6,"materialId":9,"price":600,"num":2,"costTime":1,"supplyName":"细末汽车配件","supplyListId":132},{"id":7,"materialName":"轿车轮胎","content":"细末汽车配件供货商","size":1,"icon":"UI_luntai01","lastUpdateTime":1568009993,"supplyId":6,"materialId":7,"price":1500,"num":2,"costTime":11,"supplyName":"细末汽车配件","supplyListId":133},{"id":8,"materialName":"MPV轮胎","content":"细末汽车配件供货商","size":1,"icon":"UI_luntai02","lastUpdateTime":1568009993,"supplyId":6,"materialId":8,"price":999,"num":3,"costTime":12,"supplyName":"细末汽车配件","supplyListId":134},{"id":5,"materialName":"MPV前玻璃","content":"细末汽车配件供货商","size":2,"icon":"UI_qianboli02","lastUpdateTime":1568009993,"supplyId":6,"materialId":5,"price":780,"num":2,"costTime":3,"supplyName":"细末汽车配件","supplyListId":135},{"id":4,"materialName":"轿车前玻璃","content":"细末汽车配件供货商","size":2,"icon":"UI_qianboli01","lastUpdateTime":1568009993,"supplyId":6,"materialId":4,"price":960,"num":2,"costTime":6,"supplyName":"细末汽车配件","supplyListId":136},{"id":1,"materialName":"轿车引擎","content":"细末汽车配件供货商","size":2,"icon":"UI_fadongji01","lastUpdateTime":1568009993,"supplyId":6,"materialId":1,"price":3056,"num":10,"costTime":8,"supplyName":"细末汽车配件","supplyListId":137}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 26
         * materialName : MPV方向盘
         * content : 新星汽车配件供货商
         * size : 1
         * icon : UI_fangxiangpan02
         * lastUpdateTime : 1568009900
         * supplyId : 1
         * materialId : 26
         * price : 1600
         * num : 2
         * costTime : 3
         * supplyName : 新星汽车配件
         * supplyListId : 1
         */

        private int id;
        private String materialName;
        private String content;
        private int size;
        private String icon;
        private int lastUpdateTime;
        private int supplyId;
        private int materialId;
        private int price;
        private int num;
        private int costTime;
        private String supplyName;
        private int supplyListId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(int lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public int getSupplyId() {
            return supplyId;
        }

        public void setSupplyId(int supplyId) {
            this.supplyId = supplyId;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCostTime() {
            return costTime;
        }

        public void setCostTime(int costTime) {
            this.costTime = costTime;
        }

        public String getSupplyName() {
            return supplyName;
        }

        public void setSupplyName(String supplyName) {
            this.supplyName = supplyName;
        }

        public int getSupplyListId() {
            return supplyListId;
        }

        public void setSupplyListId(int supplyListId) {
            this.supplyListId = supplyListId;
        }
    }
}
