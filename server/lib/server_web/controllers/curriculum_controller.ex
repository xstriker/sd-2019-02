defmodule ServerWeb.CurriculumController do
  use ServerWeb, :controller

  import Ecto.Query, only: [from: 2]

  alias Server.JobServer
  alias Server.JobServer.Curriculum
  alias Server.Repo

  action_fallback ServerWeb.FallbackController

  @doc """
  Método para criação de um novo currículo - recebe um json com o objeto currículo e retorna uma mensagem de sucesso
  """
  def create(conn, %{"curriculum" => curriculum_params}) do
    with {:ok, %Curriculum{} = curriculum} <- JobServer.create_curriculum(curriculum_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.curriculum_path(conn, :show, curriculum))
      |> text("Inserido com sucesso")
    end
  end

  @doc """
  Método para a atualizacao de um currículo - recebe o ID e o currículo atualizado e retorna uma mensagem de sucesso
  """
  def update(conn, %{"id" => id, "curriculum" => curriculum_params}) do
    curriculum = JobServer.get_curriculum!(id)

    with {:ok, %Curriculum{} = _curriculum} <- JobServer.update_curriculum(curriculum, curriculum_params) do
      text(conn, "Atualizado com sucesso")
    end
  end

  @doc """
  Método para listagem de currcículos - recebe a área e retorna uma lista de currículos
  """
  def getFilter(conn, %{"area" => area}) do
    list = Repo.all(from u in Curriculum, where: u.area == ^area)
    json(conn, %{"curriculuns" => list})
  end
end
