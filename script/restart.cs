using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class restart : MonoBehaviour
{
    Button button;
    public move player;
    void Start()
    {
        button = GetComponent<Button>();
        button.onClick.AddListener(OnClick);
    }
    void OnClick()
    {
        SceneManager.LoadScene("Game");
        Time.timeScale = 1;
        player.enabled = true;
    }
}
