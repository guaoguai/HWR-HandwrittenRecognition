import java.util.HashMap;
/*
 	KNN的优点在于原理简单,容易实现,对于边界不规则数据的分类效果好于线性分类器
 	缺点：
 		要保存全部数据集,需要大量的存储空间
 		需要计算每个未知点到全部已知点的距离,非常耗时
 		对于不平衡数据效果不好,需要进行改进
 		不适用于特征空间维度高的情况
 */

public class Knn {
	/*
		K最好取奇数,如果K取偶数,这时两个数据容易打成了平手,就不好判断目标点属于哪一类
		K越小越容易过拟合,当K=1时,这时只根据单个近邻进行预测,如果离目标点最近的一个点是噪声,就会出错,此时模型复杂度高,稳健性低,决策边界崎岖
		但是如果K取的过大,这时与目标点较远的样本点也会对预测起作用,就会导致欠拟合,此时模型变得简单,决策边界变平滑
	*/
	private final int K = 5;
	private int bucketLeft;			//槽内空元素
	private final KnnNode[] dist;	//最接近数值的三个样本节点


	public Knn() {

		this.bucketLeft = this.K;
		this.dist = new KnnNode[this.K];

	}

	private int index = 0; //索引值
	public void sort(int[][] sample, int[][] pixel, int dimens, String number) {

		KnnNode temp=new KnnNode(distanceCal(sample,pixel,dimens),number);

		if(bucketLeft>0) {
			//先将槽填满
			dist[index++] = temp;
			bucketLeft--;

		} else{
			//填满之后
			int flag = 0;
			double max = 0;

			for(int i = 0; i<this.K; i++) {
				if(dist[i].distance>max) {
					max = dist[i].distance;
					flag = i;

				}

			}
			//将距离最大的节点入槽
			if(max > temp.distance) dist[flag] = temp;

		}

	}//排序,选出k个最接近节点的数值

	public double distanceCal(int[][] sample, int[][] pixel, int dimens) {

		int sum=0;

		for(int i=0;i<dimens;i++) {
			for(int j=0;j<dimens;j++) {
				int s1 = sample[i][j];
				int s2 = pixel[i][j];
				sum += (s1-s2) * (s1-s2);

			}

		}
		//返回向量距离
		return Math.sqrt(sum);

	}//距离校准
	
	public String predict() {

		int max=0;
		String maxNumber="";

		HashMap<String, Integer> map= new HashMap<>();  //K为数值,V为出现字数

		for(int i = 0; i<this.K; i++) {
			map.put(dist[i].number, 0);

		}

		for(int i = 0; i<this.K; i++) {

			String tmp = dist[i].number;

			int val=map.get(tmp)+1;

			if(val > max) {
				max = val;
				maxNumber = tmp;

			}
			map.put(tmp, val);

		}
		//返回槽中出现最多次数数值
		return maxNumber;

	}//预估,返回槽中出现最多次数数值
	
}
