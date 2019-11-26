package Experiment_1;



import java.util.*;

class Process implements Comparable<Process> {
    public String name = null;
    //到达时间
    public int TArrive = 0;
    //服务时间
    public int TService = 0;
    //完成时间
    public int TComplete = 0;
    //作业周转时间
    public int Ti = 0;
    //带权周转时间
    public double W = 0;
    //判断该进程是否被处理
    public boolean isDealed = false;
    public int TRemaining = 0;
    public Process(String name, int TArrive, int TService) {
        this.name = name;
        this.TArrive = TArrive;
        this.TService = TService;
        this.TRemaining = TService;
    }

    public void setTComplete(int TComplete) {
        this.TComplete = TComplete;
    }

    public void setW(double w) {
        W = w;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", TArrive=" + TArrive +
                ", TService=" + TService +
                ", TComplete=" + TComplete +
                ", Ti=" + Ti +
                ", W=" + W +
                '}';
    }

    @Override
    public int compareTo(Process o) {
        return this.TArrive - o.TArrive;
    }
}

public class Scheduling {
    public List<Process> list = new ArrayList<>();

    public void add(String name,int t1, int t2) {
        list.add(new Process(name, t1, t2));
    }

    public Process find(String name) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).name.equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }
    public Process find(int TServiceMin) {
        for (Process aList : list) {
            if (aList.TService == TServiceMin && !aList.isDealed) {
                aList.isDealed = true;
                return aList;
            }
        }
        return null;
    }
    public void remove(String name) {
        list.remove(find(name));
    }

    //短作业优先
    public void shortService() {
        List<Process> deal = new ArrayList<>(list);
        int t = 0;
        while (!deal.isEmpty()) {
            int TServiceMin = deal.get(0).TService;
            Process toRemove = null;

            for (Process goal : deal) {
                if (goal.TArrive <= t && TServiceMin >= goal.TService) {
                    TServiceMin = goal.TService;
                    toRemove = goal;
                }
            }
            Process process = find(TServiceMin);
            process.TComplete = t + TServiceMin;
            process.Ti = process.TComplete - process.TArrive ;
            process.W = (double)process.Ti / process.TService;
            t = process.TComplete;
            deal.remove(toRemove);
        }
    }

    //时间片轮转
    public void timeRotation() {
        //时间片
        int q = 1;
        int t = 0;
        Collections.sort(list);

        LinkedList<Process> queue = new LinkedList<>();
        queue.offer(list.get(0));
        list.get(0).isDealed = true;
        t = list.get(0).TArrive;
        while (!queue.isEmpty()) {
            Process deal = queue.pollFirst();
            if(deal.TRemaining == q) {
                deal.TComplete = t + q;
                deal.TRemaining = 0;
                t += q;

            } else if (deal.TRemaining < q) {
                deal.TComplete = t + deal.TRemaining;
                t += deal.TRemaining;
                deal.TRemaining = 0;
            } else {
                deal.TComplete = t + q;
                deal.TRemaining -= q;
                t += q;
            }
            for (Process process: list) {
                if(!process.isDealed && process.TArrive <= t) {
                    queue.offer(process);
                    process.isDealed = true;
                }
            }
            if(deal.TRemaining > 0) {
                queue.addLast(deal);
            }
        }



        for (Process process : list) {
            process.Ti = process.TComplete - process.TArrive;
            process.W = (double)process.Ti / process.TService;
        }
    }


    public void display() {
        for (Process aList : list) {
            System.out.println(aList.toString());
        }
    }


}
