using System.Collections;
using UnityEngine;
using UnityEngine.UI;

public class score : MonoBehaviour
{
    public Transform player;
    public Text scoreText;
    private int hour;
    private int minutes;
    private int second;
    float time;
    private int extract;
    public int bonus;
    private bool flag = true;
    private int times;
    public GameObject panel;
    public move move;

    void Update()
    {
            if (player.position.x < -12 || player.position.x > 12)
            {
                times++;
            }
            if (times == 1)
            {
                scoreText.text = "Game Over";
                flag = false;
            }
            if (flag)
            {
                time += Time.deltaTime + extract;
                hour = (int)time / 3600;
                minutes = ((int)time - hour * 3600) / 60;
                second = (int)time - hour * 3600 - minutes * 60;
                scoreText.text = string.Format("{0:D2}:{1:D2}:{2:D2}", hour, minutes, second);
                extract = 0;
            }
            else
            {
                StartCoroutine(show());
            }
    }

    public void set()
    {
        extract += bonus;
    }

    public void end()
    {
        scoreText.text = "Game Over";
        flag = false;
    }

    IEnumerator show()
    {
        yield return new WaitForSeconds(2);
        scoreText.text = string.Format("{0:D2}:{1:D2}:{2:D2}", hour, minutes, second);
        Time.timeScale = 0;
        panel.SetActive(true);
    }

    public int getMinutes()
    {
        return minutes;
    }

    public int getSecond()
    {
        return second;
    }
}
