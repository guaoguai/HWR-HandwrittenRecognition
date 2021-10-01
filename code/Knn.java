import java.util.HashMap;
/*
 	KNN���ŵ�����ԭ���,����ʵ��,���ڱ߽粻�������ݵķ���Ч���������Է�����
 	ȱ�㣺
 		Ҫ����ȫ�����ݼ�,��Ҫ�����Ĵ洢�ռ�
 		��Ҫ����ÿ��δ֪�㵽ȫ����֪��ľ���,�ǳ���ʱ
 		���ڲ�ƽ������Ч������,��Ҫ���иĽ�
 		�������������ռ�ά�ȸߵ����
 */

public class Knn {
	/*
		K���ȡ����,���Kȡż��,��ʱ�����������״����ƽ��,�Ͳ����ж�Ŀ���������һ��
		KԽСԽ���׹����,��K=1ʱ,��ʱֻ���ݵ������ڽ���Ԥ��,�����Ŀ��������һ����������,�ͻ����,��ʱģ�͸��Ӷȸ�,�Ƚ��Ե�,���߽߱����
		�������Kȡ�Ĺ���,��ʱ��Ŀ����Զ��������Ҳ���Ԥ��������,�ͻᵼ��Ƿ���,��ʱģ�ͱ�ü�,���߽߱��ƽ��
	*/
	private final int K = 5;
	private int bucketLeft;			//���ڿ�Ԫ��
	private final KnnNode[] dist;	//��ӽ���ֵ�����������ڵ�


	public Knn() {

		this.bucketLeft = this.K;
		this.dist = new KnnNode[this.K];

	}

	private int index = 0; //����ֵ
	public void sort(int[][] sample, int[][] pixel, int dimens, String number) {

		KnnNode temp=new KnnNode(distanceCal(sample,pixel,dimens),number);

		if(bucketLeft>0) {
			//�Ƚ�������
			dist[index++] = temp;
			bucketLeft--;

		} else{
			//����֮��
			int flag = 0;
			double max = 0;

			for(int i = 0; i<this.K; i++) {
				if(dist[i].distance>max) {
					max = dist[i].distance;
					flag = i;

				}

			}
			//���������Ľڵ����
			if(max > temp.distance) dist[flag] = temp;

		}

	}//����,ѡ��k����ӽ��ڵ����ֵ

	public double distanceCal(int[][] sample, int[][] pixel, int dimens) {

		int sum=0;

		for(int i=0;i<dimens;i++) {
			for(int j=0;j<dimens;j++) {
				int s1 = sample[i][j];
				int s2 = pixel[i][j];
				sum += (s1-s2) * (s1-s2);

			}

		}
		//������������
		return Math.sqrt(sum);

	}//����У׼
	
	public String predict() {

		int max=0;
		String maxNumber="";

		HashMap<String, Integer> map= new HashMap<>();  //KΪ��ֵ,VΪ��������

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
		//���ز��г�����������ֵ
		return maxNumber;

	}//Ԥ��,���ز��г�����������ֵ
	
}
